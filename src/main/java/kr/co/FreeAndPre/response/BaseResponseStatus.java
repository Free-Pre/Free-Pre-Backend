package kr.co.FreeAndPre.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000: 요청 실패
     */
    DATABASE_ERROR(false, 2000, "데이터베이스 연동에 실패하였습니다."),
    REQUEST_ERROR(false, 2001, "입력값이 올바르지 않습니다."),

    /**
     * 3000: 회원 관련
     */
    NICKNAME_BLANCK(false, 3000, "닉네임을 입력해주세요."),
    FIRST_PERIOD_BLANCK(false, 3001, "초경 여부를 선택해주세요."),
    NO_USER(false, 3002, "존재하지 않는 회원에 대한 요청입니다."),

    /**
     * 4000: 회원 관련
     */
    NO_PERIOD(false, 4000, "월경 정보가 존재하지 않습니다."),
    NO_MONTH_PERIOD(false, 4001, "해당 월에 월경 정보가 존재하지 않습니다."),
    NO_USERSYMPTOM(false, 4002, "증상 정보가 존재하지 않습니다."),
    NO_ALARM(false, 4003, "알람 정보가 존재하지 않습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}