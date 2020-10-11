package com.kakao.throwing.controller;

import com.kakao.throwing.api.ApiResult;
import com.kakao.throwing.code.StatusCode;
import com.kakao.throwing.exception.ThrowingException;
import com.kakao.throwing.model.dto.ThrowingDto;
import com.kakao.throwing.service.ThrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ThrowingControllerTest {
    @Autowired
    private ThrowingService throwingService;

    private ThrowingController throwingController;

    @BeforeEach
    void setUp() {
        throwingController = new ThrowingController(throwingService);
    }

    @Test
    void 돈뿌리기() {
        //given
        long userId = 30020001l;
        String roomId = "room_0001";
        ThrowingDto throwingDto = ThrowingDto.builder()
                .amount(3000)
                .roomId("room_0001")
                .person(5)
                .userId(30020001l)
                .build();

        //when
        ApiResult apiResult = throwingController.sendMoney(userId, roomId, throwingDto);

        //then
        assertEquals(apiResult.getCode(), 0);
    }

    @Test
    void 돈뿌리기_인원수보다_금액이적음() {
        //given
        long userId = 30020001l;
        String roomId = "room_0001";
        ThrowingDto throwingDto = ThrowingDto.builder()
                .amount(2000)
                .roomId("room_0001")
                .person(5000)
                .userId(30020001l)
                .build();

        //when
        try {
            ApiResult apiResult = throwingController.sendMoney(userId, roomId, throwingDto);
        } catch(ThrowingException e) {
            //then
            assertEquals(e.getCode(), StatusCode.THROWING_E002.getCode());
        }
    }


    @Test
    void 돈받기_만료됐거나_존재하지않음 () {
        //given
        long userId = 30020001l;
        String roomId = "room_0001";
        String token = "kbv";

        //when
        try {
            ApiResult apiResult = throwingController.receiveMoney(userId, roomId, token);
        } catch(ThrowingException e) {
            //then
            assertEquals(e.getCode(), StatusCode.RECEIVE_E001.getCode());
        }
    }

    @Test
    void 뿌리기_조회_실패() {
        //given
        long userId = 30020001l;
        String roomId = "room_0001";
        String token = "kbv";

        //when
        try{
            ApiResult apiResult = throwingController.receiveInfomation(userId, roomId, token);
        } catch(ThrowingException e) {
            //then
            assertEquals(e.getCode(), StatusCode.RECEIVE_E005.getCode());
        }
    }
}
