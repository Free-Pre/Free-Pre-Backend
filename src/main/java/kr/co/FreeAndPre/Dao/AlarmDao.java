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




}
