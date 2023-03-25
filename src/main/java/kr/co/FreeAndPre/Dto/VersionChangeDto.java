package kr.co.FreeAndPre.Dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VersionChangeDto {
    private String msg;
    private Boolean cycle;

    public VersionChangeDto(String msg, Boolean cycle) {
        this.msg = msg;
        this.cycle = cycle;
    }
}
