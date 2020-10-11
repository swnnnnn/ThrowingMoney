package com.kakao.throwing.api;

import com.kakao.throwing.code.StatusCode;
import com.kakao.throwing.exception.ThrowingException;
import lombok.Data;

@Data
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;

    public ApiResult(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }
    public ApiResult(ThrowingException throwingException){
        this.code = throwingException.getCode();
        this.message = throwingException.getMessage();
    }

    public static ApiResult of(ThrowingException e){
        return new ApiResult(e);
    }

    public static ApiResult of(Exception e){
        return new ApiResult(StatusCode.COMMEN_E001);
    }

    public ApiResult(T data){
        this.data = data;
    }
}