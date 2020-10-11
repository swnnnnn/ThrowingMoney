package com.kakao.throwing.repository;

import com.kakao.throwing.model.entity.Receive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReceiveRepository extends JpaRepository<Receive, Long>{
    //int countByTokenAndUserIdAndRoomIdAndCreatedDate(String token, Long userId, String roomId, LocalDateTime checktime);
}