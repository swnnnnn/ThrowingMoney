package com.kakao.throwing.exception;

import com.kakao.throwing.code.StatusCode;
import lombok.Getter;

@Getter
public class ThrowingException extends RuntimeException {
    private int code;
    private String message;

    public ThrowingException(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }
}
