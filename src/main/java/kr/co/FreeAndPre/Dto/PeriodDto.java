package kr.co.FreeAndPre.Dto;

import java.sql.Date;

public class PeriodDto {
    private int period_id;
    private String email;
    private String start_date;
    private String end_date;

    public int getPeriod_id() {
        return period_id;
    }

    public String getEmail() {
        return email;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
