package kr.co.FreeAndPre.Dao;


import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;

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
    public PeriodDto insertFirstPeriod(PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Connection con = null;
        PeriodDto periodDtoResult = null;
        ResultSet rs;

        try {
            con = DBUtils.getConnection();

            /*시작 날짜와 끝나는 날짜로 기간(term) 구하기*/
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            Date start_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getStart_date());
            Date end_date = new SimpleDateFormat("yyyy.MM.dd").parse(periodDto.getEnd_date());
            long term = ((end_date.getTime() - start_date.getTime()) / 1000) / (24*60*60) + 1;

            pstmt.setString(1, Long.toString(term));
            pstmt.setString(2, periodDto.getEmail());
            pstmt.executeUpdate();

            /*period 테이블에 값 넣기*/
            String insertPeriodQuery = "insert into Period(email, start_date, end_date) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(insertPeriodQuery);

            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            pstmt.executeUpdate();

            /*result 값 가져오기*/
            String getPeriodIDQuery = "select period_id from Period where email = ? AND start_date = ? AND end_date = ?;";
            pstmt = con.prepareStatement(getPeriodIDQuery);
            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            rs = pstmt.executeQuery();

            periodDtoResult = new PeriodDto();
            while (rs.next()) {
                periodDtoResult.setPeriod_id(rs.getInt("period_id"));
            }
            periodDtoResult.setEmail(periodDto.getEmail());
            periodDtoResult.setStart_date(periodDto.getStart_date());
            periodDtoResult.setEnd_date(periodDto.getEnd_date());

            return periodDtoResult;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    2-1. 월경 정보 입력하기
     */
    public PeriodDto insertPeriod(PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Connection con = null;
        PeriodDto periodDtoResult = null;

        try {
            con = DBUtils.getConnection();

            /* period 테이블에 월경 정보 넣기 */
            String insertPeriodQuery = "insert into Period(email, start_date, end_date) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(insertPeriodQuery);

            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            pstmt.executeUpdate();

            /* User 테이블의 주기(last_cycle), 기간(term) 업데이트 */
            //최근 4개월 월경 시작일 가져오기
            pstmt = con.prepareStatement("SELECT start_date FROM Period WHERE email = ? ORDER BY end_date DESC LIMIT 4;");
            pstmt.setString(1, periodDto.getEmail());
            ResultSet rs = pstmt.executeQuery();
            List<String> start_date = new ArrayList<>();
            while(rs.next()) {
                start_date.add(rs.getString(1));
            }

            //최근 4개월 월경 마지막일 가져오기
            pstmt = con.prepareStatement("SELECT end_date FROM Period WHERE email = ? ORDER BY end_date DESC LIMIT 4;");
            pstmt.setString(1, periodDto.getEmail());
            rs = pstmt.executeQuery();
            List<String> end_date = new ArrayList<>();
            while(rs.next()) {
                end_date.add(rs.getString(1));
            }

            //user 테이블의 cycle 업데이트 쿼리문
            String updateLastCycleQuery = "UPDATE User SET cycle = ?  WHERE email = ?;";
            pstmt = con.prepareStatement(updateLastCycleQuery);

            List<Long> cycle_gap = new ArrayList<>();  //각 달의 월경 주기
            for(int i = 0; i < start_date.size() - 1; i++) {  //(새로 입력한 월경 시작일 - 저번 달의 월경 시작일)

                Long gap = (new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i + 1)).getTime()) / 1000 / (24*60*60) + 1;
                if(gap >= 50)
                    cycle_gap.add(i, Long.valueOf(28));
                else
                    cycle_gap.add(i, gap);
            }

            long cycle = 0, cycle_sum = 0;
            for(int i = 0; i < cycle_gap.size(); i++) {
                cycle_sum += cycle_gap.get(i);
                cycle = cycle_sum / cycle_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(cycle ).intValue() );
            pstmt.setString(2, periodDto.getEmail());
            pstmt.executeUpdate();

            //user 테이블의 term 업데이트 쿼리문
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            List<Long> term_gap = new ArrayList<>();  //각 달의 월경 기간
            for(int i = 0; i < start_date.size(); i++) {  //(새로 입력한 월경 마지막일 - 새로 입력한 월경 시작일)
                term_gap.add(i, (new SimpleDateFormat("yyyy-MM-dd").parse(end_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime()) / 1000 / (24*60*60) + 1);
            }
            long term = 0, term_sum = 0;
            for(int i = 0; i < term_gap.size(); i++) {
                term_sum += term_gap.get(i);
                term =  term_sum / term_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(term).intValue());
            pstmt.setString(2, periodDto.getEmail());
            pstmt.executeUpdate();

            /*result 값 가져오기*/
            String getPeriodIDQuery = "select period_id from Period where email = ? AND start_date = ? AND end_date = ?;";
            pstmt = con.prepareStatement(getPeriodIDQuery);
            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());
            rs = pstmt.executeQuery();

            periodDtoResult = new PeriodDto();
            while (rs.next()) {
                periodDtoResult.setPeriod_id(rs.getInt("period_id"));
            }
            periodDtoResult.setEmail(periodDto.getEmail());
            periodDtoResult.setStart_date(periodDto.getStart_date());
            periodDtoResult.setEnd_date(periodDto.getEnd_date());

            return periodDtoResult;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    3. 월경 정보 수정하기
     */
    public void modifyPeriod(int periodId, PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();

            /* period 테이블에 월경 정보 업데이트하기 */
            String modifyPeriodQuery = "UPDATE Period SET start_date = ?, end_date = ? WHERE period_id = ?;";
            pstmt = con.prepareStatement(modifyPeriodQuery);

            pstmt.setString(1, periodDto.getStart_date());
            pstmt.setString(2, periodDto.getEnd_date());
            pstmt.setInt(3, periodId);
            pstmt.executeUpdate();

            /* User 테이블의 주기(last_cycle), 기간(term) 업데이트 */
            //최근 4개월 월경 시작일 가져오기
            pstmt = con.prepareStatement("SELECT start_date FROM Period WHERE email = ? ORDER BY end_date DESC LIMIT 4;");
            pstmt.setString(1, periodDto.getEmail());
            ResultSet rs = pstmt.executeQuery();
            List<String> start_date = new ArrayList<>();
            while(rs.next()) {
                start_date.add(rs.getString(1));
            }

            //최근 4개월 월경 마지막일 가져오기
            pstmt = con.prepareStatement("SELECT end_date FROM Period WHERE email = ? ORDER BY end_date DESC LIMIT 4;");
            pstmt.setString(1, periodDto.getEmail());
            rs = pstmt.executeQuery();
            List<String> end_date = new ArrayList<>();
            while(rs.next()) {
                end_date.add(rs.getString(1));
            }

            //user 테이블의 cycle 업데이트 쿼리문
            String updateLastCycleQuery = "UPDATE User SET cycle = ?  WHERE email = ?;";
            pstmt = con.prepareStatement(updateLastCycleQuery);

            List<Long> cycle_gap = new ArrayList<>();  //각 달의 월경 주기
            for(int i = 0; i < start_date.size() - 1; i++) {  //(새로 입력한 월경 시작일 - 저번 달의 월경 시작일)

                Long gap = (new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i + 1)).getTime()) / 1000 / (24*60*60);
                if(gap >= 50)
                    cycle_gap.add(i, Long.valueOf(28));
                else
                    cycle_gap.add(i, gap);
            }

            long cycle = 0, cycle_sum = 0;
            for(int i = 0; i < cycle_gap.size(); i++) {
                cycle_sum += cycle_gap.get(i);
                cycle = cycle_sum / cycle_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(cycle ).intValue() );
            pstmt.setString(2, periodDto.getEmail());
            pstmt.executeUpdate();

            //user 테이블의 term 업데이트 쿼리문
            String updateTermQuery = "UPDATE User SET term = ? WHERE email = ?;";
            pstmt = con.prepareStatement(updateTermQuery);

            List<Long> term_gap = new ArrayList<>();  //각 달의 월경 기간
            for(int i = 0; i < start_date.size(); i++) {  //(새로 입력한 월경 마지막일 - 새로 입력한 월경 시작일)
                term_gap.add(i, (new SimpleDateFormat("yyyy-MM-dd").parse(end_date.get(i)).getTime() -
                        new SimpleDateFormat("yyyy-MM-dd").parse(start_date.get(i)).getTime()) / 1000 / (24*60*60) + 1);
            }
            long term = 0, term_sum = 0;
            for(int i = 0; i < term_gap.size(); i++) {
                term_sum += term_gap.get(i);
                term =  term_sum / term_gap.size();
            }

            pstmt.setInt(1, Long.valueOf(term).intValue());
            pstmt.setString(2, periodDto.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

     /*
    4. 캘린더 해당 월의 월경 정보 가져오기
     */
     public List<PeriodDto> getCalendarPeriod(String userEmail, int month) {
         PreparedStatement pstmt = null;
         Connection con = null;
         ResultSet rs = null;
         List<PeriodDto> result = new ArrayList<>();
         PeriodDto periodDto = null;

         try {
             con = DBUtils.getConnection();

             String getCalendarPeriodQuery = "SELECT period_id, email, start_date, end_date FROM Period WHERE email = ? AND (MONTH(start_date) = ? OR MONTH(end_date) = ?);";
             pstmt = con.prepareStatement(getCalendarPeriodQuery);
             pstmt.setString(1, userEmail);
             pstmt.setInt(2, month);
             pstmt.setInt(3, month);
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
   5-1. 홈화면의 cycle 정보 가져오
    */
    public int getHomeCycleInfo(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String getHomeCycleQuery = "SELECT cycle FROM User WHERE email = ?;";
            pstmt = con.prepareStatement(getHomeCycleQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();
            rs.next();

            return (rs.getObject(1, Integer.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
   5-2. 홈화면의 term 정보 가져오기
    */
    public int getHomeTermInfo(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String getHomeTermQuery = "SELECT term FROM User WHERE email = ?;";
            pstmt = con.prepareStatement(getHomeTermQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();
            rs.next();

            return (rs.getObject(1, Integer.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
   5-3. 홈화면의 start_date 정보 가져오기
    */
    public String getStartDateInfo(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String getStartDateQuery = "SELECT start_date FROM Period WHERE email = ? ORDER BY start_date DESC LIMIT 1;";
            pstmt = con.prepareStatement(getStartDateQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();
            rs.next();

            return (rs.getObject(1, String.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    validation
     */
    public Boolean getPeriodExist(int periodId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodExistQuery = "select EXISTS (select * from Period where period_id = ? limit 1) as success;";
            pstmt = con.prepareStatement(getPeriodExistQuery);
            pstmt.setInt(1, periodId);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean getPeriodExistByEmail(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodExistQuery = "select EXISTS (select * from Period where email = ? limit 1) as success;";
            pstmt = con.prepareStatement(getPeriodExistQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean getPeriodCalendarExist(String userEmail, int month) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodExistQuery = "select EXISTS (select * from Period WHERE email = ? AND (MONTH(start_date) = ? OR MONTH(end_date) = ?) limit 1) as success;";
            pstmt = con.prepareStatement(getPeriodExistQuery);
            pstmt.setString(1, userEmail);
            pstmt.setInt(2, month);
            pstmt.setInt(3, month);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






