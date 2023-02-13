package kr.co.FreeAndPre.Dto;

import java.sql.Time;

public class AlarmDto {
    private String email;
    private Time start_time;
    private Time end_time;
    private Time alarm_gap;

    public String getEmail() {
        return email;
    }

    public Time getStart_time() {
        return start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public Time getAlarm_gap() {
        return alarm_gap;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public void setAlarm_gap(Time alarm_gap) {
        this.alarm_gap = alarm_gap;
    }
}
