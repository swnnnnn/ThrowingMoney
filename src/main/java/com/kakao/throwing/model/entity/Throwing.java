package com.kakao.throwing.model.entity;

import com.kakao.throwing.model.BaseTimeEntity;
import com.kakao.throwing.model.dto.ThrowingDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Throwing extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Long userId;
    private String roomId;
    private long amount;
    private long person;

    @OneToMany(mappedBy = "throwing", cascade = CascadeType.ALL)
    private List<Receive> receivesList = new ArrayList<>();

    public Throwing(String token, ThrowingDto throwingDto) {
        this.token = token;
        this.userId = throwingDto.getUserId();
        this.roomId = throwingDto.getRoomId();
        this.amount = throwingDto.getAmount();
        this.person = throwingDto.getPerson();
    }
}