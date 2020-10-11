package com.kakao.throwing.service;

import com.kakao.throwing.exception.ThrowingException;
import com.kakao.throwing.model.dto.ThrowingDto;
import com.kakao.throwing.model.entity.Receive;
import com.kakao.throwing.model.entity.Throwing;

public interface ThrowingService {
    String sendMoney(ThrowingDto throwingDto);

    Receive receiveMoneyByToken(long userId, String roomId, String token);

    Throwing receiveInformation(long userId, String roomId, String token) throws ThrowingException;
}
