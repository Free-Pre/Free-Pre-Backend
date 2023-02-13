package kr.co.FreeAndPre.Dto;

import java.util.Date;

public class UserSymptomDto {
    private Date date;
    private String email;
    private Boolean vomit;
    private Boolean headache;
    private Boolean backache;
    private Boolean constipation;
    private Boolean giddiness;
    private Boolean tiredness;
    private Boolean fainting;
    private Boolean sensitivity;
    private Boolean acne;
    private Boolean muscular_pain;

    public Date getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVomit() {
        return vomit;
    }

    public Boolean getHeadache() {
        return headache;
    }

    public Boolean getBackache() {
        return backache;
    }

    public Boolean getConstipation() {
        return constipation;
    }

    public Boolean getGiddiness() {
        return giddiness;
    }

    public Boolean getTiredness() {
        return tiredness;
    }

    public Boolean getFainting() {
        return fainting;
    }

    public Boolean getSensitivity() {
        return sensitivity;
    }

    public Boolean getAcne() {
        return acne;
    }

    public Boolean getMuscular_pain() {
        return muscular_pain;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVomit(Boolean vomit) {
        this.vomit = vomit;
    }

    public void setHeadache(Boolean headache) {
        this.headache = headache;
    }

    public void setBackache(Boolean backache) {
        this.backache = backache;
    }

    public void setConstipation(Boolean constipation) {
        this.constipation = constipation;
    }

    public void setGiddiness(Boolean giddiness) {
        this.giddiness = giddiness;
    }

    public void setTiredness(Boolean tiredness) {
        this.tiredness = tiredness;
    }

    public void setFainting(Boolean fainting) {
        this.fainting = fainting;
    }

    public void setSensitivity(Boolean sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setAcne(Boolean acne) {
        this.acne = acne;
    }

    public void setMuscular_pain(Boolean muscular_pain) {
        this.muscular_pain = muscular_pain;
    }
}
