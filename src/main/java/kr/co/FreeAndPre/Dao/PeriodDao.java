package kr.co.FreeAndPre.Dao;


import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Model.GetPeriodRes;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Repository;

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

    public PeriodDto getAbuseByIdx(int periodId) {
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
                periodDto.setStart_date(rs.getDate("start_date"));
                periodDto.setEnd_date(rs.getDate("end_date"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodDto;
    }
}






