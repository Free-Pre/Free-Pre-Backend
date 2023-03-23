package kr.co.FreeAndPre.Dao;

import kr.co.FreeAndPre.DBUtils;
import kr.co.FreeAndPre.Dto.AlarmDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlarmDao {
    private static AlarmDao instance = new AlarmDao();
    public AlarmDao() {}
    public static AlarmDao getInstance() { return instance; }

//    1. 알람 가져오기
    public AlarmDto getAlarm(String email){
        AlarmDto alarmDto = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String getAlarmByEmailQuery = "SELECT email, start_time, end_time, alarm_gap FROM Alarm WHERE email = ?;";
            pstmt = con.prepareStatement(getAlarmByEmailQuery);

            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if(rs.next()){
                alarmDto = new AlarmDto();
                alarmDto.setEmail(rs.getString("email"));
                alarmDto.setStart_time(rs.getTime("start_time"));
                alarmDto.setEnd_time(rs.getTime("end_time"));
                alarmDto.setAlarm_gap(rs.getTime("alarm_gap"));

            }
            else{
                alarmDto = null;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return alarmDto;
    }


    //     2. 알람 저장하기
    public int saveAlarm(AlarmDto alarmDto){
        PreparedStatement pstmt = null;
        Connection con = null;

        try {

            con = DBUtils.getConnection();

            // 중복된 알람 저장 시 ignore 처리

            String saveAlarmQuery = "INSERT IGNORE INTO Alarm (email, start_time, end_time, alarm_gap) VALUES (?, ?, ?, ?) ;";
            pstmt = con.prepareStatement(saveAlarmQuery);

            pstmt.setString(1,alarmDto.getEmail());
            pstmt.setTime(2,alarmDto.getStart_time());
            pstmt.setTime(3,alarmDto.getEnd_time());
            pstmt.setTime(4,alarmDto.getAlarm_gap());

            return pstmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //    3. 알람 수정하기
    public void editAlarm(String email, AlarmDto alarmDto){
        PreparedStatement pstmt = null;
        Connection con = null;

        try{
            con = DBUtils.getConnection();

            String editAlarmQuery = "UPDATE Alarm SET start_time = ?, end_time = ?, alarm_gap = ? WHERE email = ?;";
            pstmt = con.prepareStatement(editAlarmQuery);

            pstmt.setTime(1, alarmDto.getStart_time());
            pstmt.setTime(2, alarmDto.getEnd_time());
            pstmt.setTime(3, alarmDto.getAlarm_gap());
            pstmt.setString(4, email);

            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean getAlarmExist (String email) {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String getAlarmExistQuery = "SELECT EXISTS (SELECT * From Alarm WHERE email = ? limit 1) as success; ";
            pstmt = con.prepareStatement(getAlarmExistQuery);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            rs.next();

            return (rs.getObject(1, Boolean.class));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }






}
