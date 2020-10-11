package com.kakao.throwing.repository;

import com.kakao.throwing.model.entity.Throwing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ThrowingRepository extends JpaRepository<Throwing, Long> {
    int countByTokenAndUserIdAndRoomIdAndCreatedDateAfter(String token, Long userId, String roomId, LocalDateTime checkTime);

    Throwing findByTokenAndRoomIdAndCreatedDateAfter(String token, String roomId, LocalDateTime checkTime);

    Throwing findByUserIdAndRoomIdAndTokenAndCreatedDateAfter(Long userId, String roomId, String token, LocalDateTime checkTime);
}