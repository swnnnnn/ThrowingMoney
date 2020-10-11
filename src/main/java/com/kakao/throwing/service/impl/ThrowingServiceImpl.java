package com.kakao.throwing.service.impl;

import com.kakao.throwing.code.StatusCode;
import com.kakao.throwing.exception.ThrowingException;
import com.kakao.throwing.model.dto.ThrowingDto;
import com.kakao.throwing.model.entity.Receive;
import com.kakao.throwing.model.entity.Throwing;
import com.kakao.throwing.repository.ReceiveRepository;
import com.kakao.throwing.repository.ThrowingRepository;
import com.kakao.throwing.service.ThrowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class ThrowingServiceImpl implements ThrowingService {

    private final ThrowingRepository throwingRepository;
    private final ReceiveRepository receiveRepository;

    @Transactional
    public String sendMoney(ThrowingDto throwingDto) {
        if(throwingDto.getPerson() < 1 || throwingDto.getAmount() < 1){
            throw new ThrowingException(StatusCode.THROWING_E001);
        }

        if(throwingDto.getPerson() > throwingDto.getAmount()){
            throw new ThrowingException(StatusCode.THROWING_E002);
        }

        String token = this.createToken(throwingDto);

        Throwing throwing = new Throwing(token, throwingDto);

        throwingRepository.save(throwing);

        receiveRepository.saveAll(divideMoney(throwing));

        return token;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Receive receiveMoneyByToken(long userId, String roomId, String token) {
        Receive receive = null;

        LocalDateTime time = LocalDateTime.now().minusMinutes(10);
        Throwing throwing = throwingRepository.findByTokenAndRoomIdAndCreatedDateAfter(token, roomId, time);
        if(throwing == null) {
            throw new ThrowingException(StatusCode.RECEIVE_E001);
        }

        if(throwing.getUserId().equals(userId)){
            throw new ThrowingException(StatusCode.RECEIVE_E002);
        }

        for(Receive receiveMoney : throwing.getReceivesList()) {
            if(receiveMoney.getReceiveUserId() == userId){
                throw new ThrowingException(StatusCode.RECEIVE_E003);
            } else if(receiveMoney.getReceiveUserId() == 0 && receive == null) {
                receive = receiveMoney;
            }
        }

        if(receive == null){
            throw new ThrowingException(StatusCode.RECEIVE_E004);
        }

        receive.saveReceiveUserId(userId);

        receiveRepository.save(receive);

        return receive;
    }

    public Throwing receiveInformation(long userId, String roomId, String token) throws ThrowingException {
        LocalDateTime time = LocalDateTime.now().minusDays(7);
        Throwing throwing = throwingRepository.findByUserIdAndRoomIdAndTokenAndCreatedDateAfter(userId, roomId, token, time);
        if(throwing == null) {
            throw new ThrowingException(StatusCode.RECEIVE_E005);
        }

        return throwing;
    }

    private String createToken(ThrowingDto throwingDto) {
        String token = RandomStringUtils.randomAlphabetic(3);
        LocalDateTime time = LocalDateTime.now().minusMinutes(10);
        while(throwingRepository.countByTokenAndUserIdAndRoomIdAndCreatedDateAfter(token, throwingDto.getUserId(), throwingDto.getRoomId(), time) > 0){
            token = RandomStringUtils.randomAlphabetic(3);
        }
        return token;
    }

    public List<Receive> divideMoney(Throwing throwing){
        List<Receive> divs = new ArrayList<Receive>();

        int totalAmout = (int) throwing.getAmount();

        Random rand = new Random();
        for (int i=0; i<throwing.getPerson()-1; i++) {
            divs.add(new Receive(throwing, 0, rand.nextInt(totalAmout)));
            totalAmout -= divs.get(i).getAmount();
        }
        divs.add(new Receive(throwing, 0, totalAmout));

        return divs;
    }
}