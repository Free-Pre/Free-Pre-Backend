package kr.co.FreeAndPre.Dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HomePeriodDto {
    private int cycle;
    private int term;
    private String start_date;

    public HomePeriodDto(int cycle, int term, String start_date) {
        this.cycle = cycle;
        this.term = term;
        this.start_date = start_date;
    }
}
