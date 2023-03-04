package kr.co.FreeAndPre.Dao;


import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PeriodDao {
    private static PeriodDao instance = new PeriodDao();
    public PeriodDao() {}
    public static PeriodDao getInstance() {
        return instance;
    }

    /*
    1. 월경 정보 가져오기
     */
    public List<PeriodDto> getPeriodInfoByEmail(String userEmail) {
        PeriodDto periodDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        List<PeriodDto> result = new ArrayList<>();

        try {
            con = DBUtils.getConnection();

            //최근 4개월의 월경 정보 가져오기
            String getPeriodByEmailQuery = "SELECT period_id, email, start_date, end_date FROM Period WHERE email = ? " +
                    "ORDER BY start_date DESC LIMIT 4;";
            pstmt = con.prepareStatement(getPeriodByEmailQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                periodDto = new PeriodDto();
                periodDto.setPeriod_id(rs.getInt("period_id"));
                periodDto.setEmail(rs.getString("email"));
                periodDto.setStart_date(rs.getString("start_date"));
                periodDto.setEnd_date(rs.getString("end_date"));
                result.add(periodDto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /*
    2. 회원가입 직후 첫 월경일 정보 입력
     */
    public int insertFirstPeriod(PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();

            /*시작 날짜와 끝나는 날짜로 기간(term) 구하기*/
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            Date start_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getStart_date());
            Date end_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getEnd_date());
            long term = ((end_date.getTime() - start_date.getTime()) / 1000) / (24*60*60);

            pstmt.setString(1, Long.toString(term));
            pstmt.setString(2, periodDto.getEmail());
            int res = pstmt.executeUpdate();

            /*period 테이블에 값 넣기*/
            String insertPeriodQuery = "insert into Period(email, start_date, end_date) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(insertPeriodQuery);

            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            res += pstmt.executeUpdate();

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    2-1. 월경 정보 입력하기
     */
    public int insertPeriod(PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();

            /* period 테이블에 월경 정보 넣기 */
            String insertPeriodQuery = "insert into Period(email, start_date, end_date) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(insertPeriodQuery);

            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            int res = pstmt.executeUpdate();

            /* User 테이블의 주기(last_cycle), 기간(term) 업데이트 */
            stmt = con.createStatement();

            //최근 4개월 월경 시작일 가져오기
            ResultSet rs = stmt.executeQuery("SELECT start_date FROM period ORDER BY end_date DESC LIMIT 4;");
            List<String> start_date = new ArrayList<>();
            while(rs.next()) {
                start_date.add(rs.getString(1));
            }

            //최근 4개월 월경 마지막일 가져오기
            rs = stmt.executeQuery("SELECT end_date FROM period ORDER BY end_date DESC LIMIT 4;");
            List<String> end_date = new ArrayList<>();
            while(rs.next()) {
                end_date.add(rs.getString(1));
            }

            //user 테이블의 cycle 업데이트 쿼리문
            String updateLastCycleQuery = "UPDATE User SET cycle = ?  WHERE email = ?;";
            pstmt = con.prepareStatement(updateLastCycleQuery);

            List<Long> cycle_gap = new ArrayList<>();  //각 달의 월경 주기
            for(int i = 0; i < start_date.size() - 1; i++) {  //(새로 입력한 월경 시작일 - 저번 달의 월경 시작일)
                cycle_gap.add(i, (new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i + 1)).getTime()) / 1000 / (24*60*60));
            }
            long cycle = 0, cycle_sum = 0;
            for(int i = 0; i < cycle_gap.size(); i++) {
                cycle_sum += cycle_gap.get(i);
                cycle = cycle_sum / cycle_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(cycle ).intValue() );
            pstmt.setString(2, periodDto.getEmail());
            res = pstmt.executeUpdate();

            //user 테이블의 term 업데이트 쿼리문
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            List<Long> term_gap = new ArrayList<>();  //각 달의 월경 기간
            for(int i = 0; i < start_date.size(); i++) {  //(새로 입력한 월경 마지막일 - 새로 입력한 월경 시작일)
                term_gap.add(i, (new SimpleDateFormat("yyyy-MM-dd").parse(end_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime()) / 1000 / (24*60*60));
            }
            long term = 0, term_sum = 0;
            for(int i = 0; i < term_gap.size(); i++) {
                term_sum += term_gap.get(i);
                term =  term_sum / term_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(term).intValue());
            pstmt.setString(2, periodDto.getEmail());
            res += pstmt.executeUpdate();

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    3. 월경 정보 수정하기
     */
    public int modifyPeriod(int periodId, PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String modifyPeriodQuery = "UPDATE Period SET start_date = ?, end_date = ? WHERE period_id = ?;";
            pstmt = con.prepareStatement(modifyPeriodQuery);

            pstmt.setString(1, periodDto.getStart_date());
            pstmt.setString(2, periodDto.getEnd_date());
            pstmt.setInt(3, periodId);

            //+ user 테이블의 기간, 주기 업데이트

            int res = pstmt.executeUpdate();
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






