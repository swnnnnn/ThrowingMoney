package com.kakao.throwing.model.entity;

import com.kakao.throwing.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receive extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Throwing.class, fetch = FetchType.EAGER)
    private Throwing throwing;

    private long amount;
    private long receiveUserId;
    private LocalDateTime receivedDate;

    public Receive(Throwing throwing, long receiveUserId, long amount) {
        this.throwing = throwing;
        this.receiveUserId = receiveUserId;
        this.amount = amount;
    }

    public void saveReceiveUserId(Long receiveUserId){
        this.receiveUserId = receiveUserId;
        this.receivedDate = LocalDateTime.now();
        //return amount;
    }
}