package com.kakao.throwing.controller;

import com.kakao.throwing.api.ApiResult;
import com.kakao.throwing.model.dto.ResponseDto;
import com.kakao.throwing.model.dto.ThrowingDto;
import com.kakao.throwing.model.entity.Receive;
import com.kakao.throwing.model.entity.Throwing;
import com.kakao.throwing.service.ThrowingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class ThrowingController {

    private final ThrowingService throwingService;

    public ThrowingController(ThrowingService throwingService) {
        this.throwingService = throwingService;
    }

    @PostMapping("/send")
    public ApiResult sendMoney( @RequestHeader("X-USER-ID") Long userId,
                             @RequestHeader("X-ROOM-ID") String roomId,
                             @RequestBody ThrowingDto throwingDto) {

        throwingDto.setUserId(userId);
        throwingDto.setRoomId(roomId);

        ApiResult result = new ApiResult(new ResponseDto(throwingService.sendMoney(throwingDto)));
        return result;
    }

    @PutMapping("/receive/{token}")
    public ApiResult receiveMoney(@RequestHeader("X-USER-ID") Long userId,
                                  @RequestHeader("X-ROOM-ID") String roomId,
                                  @PathVariable("token") String token) {

        Receive receive = throwingService.receiveMoneyByToken(userId, roomId, token);

        ApiResult result = new ApiResult(new ResponseDto(receive.getAmount()));
        return result;
    }

    @GetMapping("/receive/{token}")
    public ApiResult receiveInfomation(@RequestHeader("X-USER-ID") Long userId,
                                 @RequestHeader("X-ROOM-ID") String roomId,
                                 @PathVariable("token") String token) {

        Throwing throwing = throwingService.receiveInformation(userId, roomId, token);

        ApiResult result = new ApiResult(new ResponseDto(throwing));
        return result;
    }
}
