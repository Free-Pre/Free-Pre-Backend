package kr.co.FreeAndPre.Dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserInfoDto {
    private String nickname;
    private Boolean notice;
    private Boolean pregnancy;

    public UserInfoDto(String nickname, Boolean notice, Boolean pregnancy) {
        this.nickname = nickname;
        this.notice = notice;
        this.pregnancy = pregnancy;
    }
}
