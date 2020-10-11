package com.kakao.throwing.handler;

import com.kakao.throwing.api.ApiResult;
import com.kakao.throwing.exception.ThrowingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ThrowingExceptionHandler {

    @ExceptionHandler(ThrowingException.class)
    public ResponseEntity<ApiResult> handleMoneyPlexException(ThrowingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult> handleUnauthorized(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResult.of(e));
    }
}
