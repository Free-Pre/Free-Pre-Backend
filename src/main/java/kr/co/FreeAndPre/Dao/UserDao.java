package kr.co.FreeAndPre.Dao;

import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static UserDao instance = new UserDao();
    public UserDao() {}
    public static UserDao getInstance() {
        return instance;
    }

    /*
    1. 회원 존재 여부 확인하기
     */
    public Boolean getUserExist(String userEmail) {
        UserDto userDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getPeriodByIdQuery = "select EXISTS (select * from User where email = ? limit 1) as success;";
            String getUserExistParams = userEmail;
            pstmt = con.prepareStatement(getPeriodByIdQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
