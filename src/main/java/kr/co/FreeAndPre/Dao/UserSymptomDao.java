package kr.co.FreeAndPre.Dao;

import com.mysql.cj.protocol.Resultset;
import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.UserSymptomDto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;


public class UserSymptomDao {
    private static UserSymptomDao instance = new UserSymptomDao();
    public UserSymptomDao() {}
    public static UserSymptomDao getInstance(){
        return instance;
    }

// 증상 가져오기
    public UserSymptomDto getUserSymptom(String email, String date){
        UserSymptomDto userSymptomDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            // email과 date에 맞는 증상 가져오기
            String getUserSymptomByEDQuery = "SELECT vomit, headache, backache, constipation, giddiness, tiredness, fainting, sensitivity, acne, muscular_pain From UserSymptom WHERE email =? and date=?;";
            pstmt = con.prepareStatement(getUserSymptomByEDQuery);

            pstmt.setString(1, email);
            pstmt.setString(2, date);
            rs = pstmt.executeQuery();


            if(rs.next()){
                userSymptomDto = new UserSymptomDto();
//            userSymptomDto.setEmail(rs.getString("email"));
//            userSymptomDto.setDate(rs.getString("date"));
//            email, date 추가시 error 500 발생
                userSymptomDto.setVomit(rs.getBoolean("vomit"));
                userSymptomDto.setHeadache(rs.getBoolean("headache"));
                userSymptomDto.setBackache(rs.getBoolean("backache"));
                userSymptomDto.setConstipation(rs.getBoolean("constipation"));
                userSymptomDto.setGiddiness(rs.getBoolean("giddiness"));
                userSymptomDto.setTiredness(rs.getBoolean("tiredness"));
                userSymptomDto.setFainting(rs.getBoolean("fainting"));
                userSymptomDto.setSensitivity(rs.getBoolean("sensitivity"));
                userSymptomDto.setAcne(rs.getBoolean("acne"));
                userSymptomDto.setMuscular_pain(rs.getBoolean("muscular_pain"));

            }
            else{
                userSymptomDto = null;
            }


        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return userSymptomDto;

    }

//    증상 등록

    public int insertUserSymptom(UserSymptomDto userSymptomDto){
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();

            String insertUserSymptomQuery = "INSERT INTO UserSymptom(email, date, vomit, headache, backache, constipation, giddiness, tiredness, fainting, sensitivity, acne, muscular_pain) Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";
            pstmt = con.prepareStatement(insertUserSymptomQuery);

            pstmt.setString(1, userSymptomDto.getEmail());
            pstmt.setString(2,  userSymptomDto.getDate());
            pstmt.setBoolean(3, userSymptomDto.getVomit());
            pstmt.setBoolean(4, userSymptomDto.getHeadache());
            pstmt.setBoolean(5, userSymptomDto.getBackache());
            pstmt.setBoolean(6, userSymptomDto.getConstipation());
            pstmt.setBoolean(7, userSymptomDto.getGiddiness());
            pstmt.setBoolean(8,userSymptomDto.getTiredness());
            pstmt.setBoolean(9, userSymptomDto.getFainting());
            pstmt.setBoolean(10, userSymptomDto.getSensitivity());
            pstmt.setBoolean(11, userSymptomDto.getAcne());
            pstmt.setBoolean(12, userSymptomDto.getMuscular_pain());


            int res = pstmt.executeUpdate();
            return res;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

//    증상 수정

    public void editUserSymptom(String email, String date, UserSymptomDto userSymptomDto){
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBUtils.getConnection();

            String editUserSymptomQuery = "UPDATE UserSymptom SET vomit = ?, headache = ?, backache = ?, constipation = ?, giddiness = ?, tiredness = ?, fainting = ?, sensitivity = ?, acne = ?, muscular_pain = ? WHERE email = ? and date = ?;";
            pstmt = con.prepareStatement(editUserSymptomQuery);


            pstmt.setBoolean(1,userSymptomDto.getVomit());
            pstmt.setBoolean(2, userSymptomDto.getHeadache());
            pstmt.setBoolean(3, userSymptomDto.getBackache());
            pstmt.setBoolean(4, userSymptomDto.getConstipation());
            pstmt.setBoolean(5, userSymptomDto.getGiddiness());
            pstmt.setBoolean(6,userSymptomDto.getTiredness());
            pstmt.setBoolean(7, userSymptomDto.getFainting());
            pstmt.setBoolean(8, userSymptomDto.getSensitivity());
            pstmt.setBoolean(9, userSymptomDto.getAcne());
            pstmt.setBoolean(10, userSymptomDto.getMuscular_pain());
            pstmt.setString(11, email);
            pstmt.setString(12, date);

            pstmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
