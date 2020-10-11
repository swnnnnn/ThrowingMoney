package com.kakao.throwing.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ThrowingDto {
    private Long userId;
    private String roomId;
    private long amount;
    private long person;
}