package kr.co.FreeAndPre.Dto;

public class UserDto {
    private String email;
    private String nickname;
    private Boolean first_period;
    private int average_cycle;
    private int last_cycle;
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

    public int getAverage_cycle() {
        return average_cycle;
    }

    public int getLast_cycle() {
        return last_cycle;
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

    public void setAverage_cycle(int average_cycle) {
        this.average_cycle = average_cycle;
    }

    public void setLast_cycle(int last_cycle) {
        this.last_cycle = last_cycle;
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
