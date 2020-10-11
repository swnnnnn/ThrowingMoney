package com.kakao.throwing.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReceiveDetailDto {
    private Long amount;
    private Long receiveUserId;
}