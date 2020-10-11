package com.kakao.throwing.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.throwing.model.entity.Throwing;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String token;
    private String createdDate;
    private Long amount;
    private Long receiveMoney;
    private List<ReceiveDetailDto> receiveDetails;

    public ResponseDto(String token){
        this.token = token;
    }

    public ResponseDto(Long receiveMoney){
        this.receiveMoney = receiveMoney;
    }

    public ResponseDto(Throwing throwing){
        this.amount = throwing.getAmount();
        this.createdDate = throwing.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.receiveMoney = 0l;
        this.receiveDetails = new ArrayList<ReceiveDetailDto>();
        throwing.getReceivesList().forEach((rm) -> {
            if(rm.getReceiveUserId() != 0){
                this.receiveMoney += rm.getAmount();
                receiveDetails.add(new ReceiveDetailDto(rm.getAmount(), rm.getReceiveUserId()));
            }
        });
    }
}