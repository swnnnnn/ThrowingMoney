package com.kakao.throwing.code;

public enum StatusCode {
    COMMEN_E001(0001, "Internal Server Error"),

    THROWING_E001(1001, "금액이나 인원수를 확인해주시기 바랍니다."),
    THROWING_E002(1002, "뿌리기 금액이 인원수 보다 작습니다."),

    RECEIVE_E001(2001, "이미 만료 되었거나 존재하지 않는 받기 입니다."),
    RECEIVE_E002(2002, "본인이 뿌리기 한 금액은 받을 수 없습니다."),
    RECEIVE_E003(2003, "이미 돈을 받았습니다."),
    RECEIVE_E004(2004, "선착순 받기가 모두 끝났습니다."),
    RECEIVE_E005(2005, "요청하신 토큰이 7일이 경과 하였거나, 유효하지 않은 토큰 입니다.")
    ;

    private int code;
    private String message;

    StatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}
