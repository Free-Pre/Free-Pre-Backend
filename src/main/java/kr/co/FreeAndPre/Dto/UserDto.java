package kr.co.FreeAndPre.Dto;

public class UserDto {
    private String email;
    private String nickname;
    private Boolean first_period;
    private int cycle;
    private int term;
    private Boolean notice;
    private Boolean pregnancy;

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public Boolean getFirst_period() {
        return first_period;
    }

    public int getCycle() {
        return cycle;
    }

    public int getTerm() {
        return term;
    }

    public Boolean getNotice() {
        return notice;
    }

    public Boolean getPregnancy() {
        return pregnancy;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFirst_period(Boolean first_period) {
        this.first_period = first_period;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setNotice(Boolean notice) {
        this.notice = notice;
    }

    public void setPregnancy(Boolean pregnancy) {
        this.pregnancy = pregnancy;
    }
}
