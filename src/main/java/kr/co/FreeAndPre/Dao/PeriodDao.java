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

            /* User 테이블의 마지막 월경 주기(last_cycle) 업데이트 */
            stmt = con.createStatement();
            //최근 월경일의 마지막 날
            ResultSet rs = stmt.executeQuery("SELECT end_date FROM period ORDER BY end_date DESC LIMIT 1;");
            String last_end_date = "0";
            while(rs.next()) {
                last_end_date = rs.getString(1);
            }

            String updateLastCycleQuery = "UPDATE User SET last_cycle = ?  WHERE email = ?;";
            pstmt = con.prepareStatement(updateLastCycleQuery);

            //최근 월경일의 마지막 날
            Date old_end_date = new SimpleDateFormat("yyyy-MM-dd").parse(last_end_date);
            //새로 입력한 월경일의 첫째 날
            Date new_start_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getStart_date());
            //최근 월경 주기
            long update_end_date = ((new_start_date.getTime() - old_end_date.getTime()) / 1000) / (24*60*60);

            pstmt.setString(1, Long.toString(update_end_date));
            pstmt.setString(2, periodDto.getEmail());
            int res = pstmt.executeUpdate();

            /* User 테이블의 마지막 월경 주기(last_cycle) 업데이트 */
            String updateAverageCycleQuery = "UPDATE User SET average_cycle = ((average_cycle + ?) / 2) WHERE email = ?;";
            pstmt = con.prepareStatement(updateAverageCycleQuery);

            pstmt.setString(1, Long.toString(update_end_date));
            pstmt.setString(2, periodDto.getEmail());
            res += pstmt.executeUpdate();

            /*시작 날짜와 끝나는 날짜로 기간(term) 구하기*/
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            Date start_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getStart_date());
            Date end_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getEnd_date());
            long term = ((end_date.getTime() - start_date.getTime()) / 1000) / (24*60*60);

            pstmt.setString(1, Long.toString(term));
            pstmt.setString(2, periodDto.getEmail());
            res += pstmt.executeUpdate();

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
}






