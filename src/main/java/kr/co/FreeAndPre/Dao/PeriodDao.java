package kr.co.FreeAndPre.Dao;


import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodDao {
    private static PeriodDao instance = new PeriodDao();
    public PeriodDao() {}
    public static PeriodDao getInstance() {
        return instance;
    }

    /*
    1. 월경 정보 가져오기
     */
    public PeriodDto getPeriodById(int periodId) {
        PeriodDto periodDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodByIdQuery = "SELECT period_id, email, start_date, end_date FROM Period WHERE period_id = ?;";
            int getPeriodByIdParams = periodId;
            pstmt = con.prepareStatement(getPeriodByIdQuery);
            pstmt.setInt(1, periodId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                periodDto = new PeriodDto();
                periodDto.setPeriod_id(rs.getInt("period_id"));
                periodDto.setEmail(rs.getString("email"));
                periodDto.setStart_date(rs.getString("start_date"));
                periodDto.setEnd_date(rs.getString("end_date"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodDto;
    }

    /*
    2. 월경 정보 입력하기
     */
    public int insertPeriod(PeriodDto periodDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodByIdQuery = "insert into Period(email, start_date, end_date) VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(getPeriodByIdQuery);

            pstmt.setString(1, periodDto.getEmail());
            pstmt.setString(2, periodDto.getStart_date());
            pstmt.setString(3, periodDto.getEnd_date());

            int res = pstmt.executeUpdate();
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






