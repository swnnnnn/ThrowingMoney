package com.kakao.throwing.service.impl;

import com.kakao.throwing.model.dto.ThrowingDto;
import com.kakao.throwing.model.entity.Receive;
import com.kakao.throwing.model.entity.Throwing;
import com.kakao.throwing.repository.ReceiveRepository;
import com.kakao.throwing.repository.ThrowingRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ThrowingServiceImplTest {

    @Autowired
    private ThrowingRepository throwingRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

//    public ThrowingServiceImplTest(ThrowingRepository throwingRepository,
//                                   ReceiveRepository receiveRepository) {
//        this.throwingRepository = throwingRepository;
//        this.receiveRepository = receiveRepository;
//    }

    private ThrowingServiceImpl throwingServiceImpl;

    private ThrowingDto throwingDto;
    private Throwing throwing;

    @BeforeEach
    public void 초기화() {
        throwingDto = ThrowingDto.builder()
                .amount(3000)
                .roomId("room_0001")
                .person(3)
                .userId(30020001l)
                .build();
        throwingServiceImpl = new ThrowingServiceImpl(throwingRepository, receiveRepository);
    }

    @Test
    void 돈뿌리기_저장() {
        //given
        String token = RandomStringUtils.randomAlphabetic(3);

        this.throwing = new Throwing(token, throwingDto);

        //when
        throwingRepository.save(throwing);

        //then
        assertNotNull(throwing.getId());
        assertEquals(throwing.getAmount(), throwingDto.getAmount());
        assertEquals(throwing.getRoomId(), throwingDto.getRoomId());
        assertEquals(throwing.getPerson(), throwingDto.getPerson());
        assertEquals(throwing.getUserId(), throwingDto.getUserId());
    }

    @Test
    void 돈뿌리기_인원수에따라_돈나누기() {
        //given
        String token = RandomStringUtils.randomAlphabetic(3);
        final Throwing throwing = new Throwing(token, throwingDto);

        //when
        List<Receive> list = throwingServiceImpl.divideMoney(throwing);
        assertEquals(list.size(), throwingDto.getPerson());
        AtomicReference<Long> amount = new AtomicReference<>(0l);
        list.forEach((rm) -> {
            amount.updateAndGet(v -> v + rm.getAmount());
        });

        //then
        assertEquals(amount.get(), throwingDto.getAmount());
    }

    @Test
    void 돈뿌리기_정보조회() {
        //given
        돈뿌리기_저장();

        //when
        Throwing tmpThrowing = throwingServiceImpl.receiveInformation(throwing.getUserId(), throwing.getRoomId(), throwing.getToken());

        //then
        assertEquals(tmpThrowing, throwing);
    }


    @Test
    void 돈뿌리기_저장후_토큰확인() {
        //when
        String token = throwingServiceImpl.sendMoney(throwingDto);

        //then
        System.out.println(token);
    }
}