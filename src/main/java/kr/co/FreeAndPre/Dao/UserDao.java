package kr.co.FreeAndPre.Dao;

import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            String getUserByIdQuery = "select EXISTS (select * from User where email = ? limit 1) as success;";
            pstmt = con.prepareStatement(getUserByIdQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    2. Free 회원 가입하기
     */
    public void insertFreeUser(UserDto userDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String makeFreeUserByIdQuery = "insert into User(email, nickname, first_period, cycle) " +
                    "VALUES (?, ?, ?, ?);";
            pstmt = con.prepareStatement(makeFreeUserByIdQuery);

            pstmt.setString(1, userDto.getEmail());
            pstmt.setString(2, userDto.getNickname());
            pstmt.setBoolean(3, userDto.getFirst_period());
            pstmt.setInt(4, 28);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    2-1. Pre 회원 가입하기
     */
    public void insertPreUser(UserDto userDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String makePreUserByIdQuery = "insert into User(email, nickname, first_period) " +
                    "VALUES (?, ?, ?);";
            pstmt = con.prepareStatement(makePreUserByIdQuery);

            pstmt.setString(1, userDto.getEmail());
            pstmt.setString(2, userDto.getNickname());
            pstmt.setBoolean(3, userDto.getFirst_period());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    3. 회원 닉네임 수정하기
     */
    public void modifyUserNickname(String userEmail, UserDto userDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String modifyUserQuery = "UPDATE User SET nickname = ? WHERE email = ?;";
            pstmt = con.prepareStatement(modifyUserQuery);

            pstmt.setString(1, userDto.getNickname());
            pstmt.setString(2, userEmail);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    4. Pre->Free 버전 수정하기
     */
    public void pretofree(String userEmail, UserDto userDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String modifyUserQuery = "UPDATE User SET first_period = ?, cycle = ? WHERE email = ?;";
            pstmt = con.prepareStatement(modifyUserQuery);

            pstmt.setBoolean(1, userDto.getFirst_period());
            pstmt.setInt(2, 28);
            pstmt.setString(3, userEmail);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    4-1. Free->Pre 버전 수정하기
     */
    public void freetopre(String userEmail, UserDto userDto) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String modifyUserQuery = "UPDATE User SET first_period = ? WHERE email = ?;";
            pstmt = con.prepareStatement(modifyUserQuery);

            pstmt.setBoolean(1, userDto.getFirst_period());
            pstmt.setString(2, userEmail);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    5. 회원 탈퇴하기
     */
    public void deleteUser(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();
            String deleteUserQuery = "DELETE FROM User WHERE email = ?;";

            pstmt = con.prepareStatement(deleteUserQuery);
            pstmt.setString(1, userEmail);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
   6. 사용자 주기 확인
    */
    public Boolean getUserCycle(String userEmail) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs;

        try {
            con = DBUtils.getConnection();
            String deleteUserQuery = "select ifnull(cycle, false) cycle from User where email = ?;";

            pstmt = con.prepareStatement(deleteUserQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    7. 사용자 정보 가져오기
     */
    public UserDto getUserInfo(String userEmail) {
        UserDto userDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            //최근 4개월의 월경 정보 가져오기
            String getUserInfoQuery = "SELECT * FROM User WHERE email = ?;";
            pstmt = con.prepareStatement(getUserInfoQuery);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                userDto = new UserDto();
                userDto.setEmail(rs.getString("email"));
                userDto.setNickname(rs.getString("nickname"));
                userDto.setFirst_period(rs.getBoolean("first_period"));
                userDto.setCycle(rs.getInt("cycle"));
                userDto.setTerm(rs.getInt("term"));
                userDto.setNotice(rs.getBoolean("notice"));
                userDto.setPregnancy(rs.getBoolean("pregnancy"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }
}
