package com.example.receptionservice.Repository;

import com.example.receptionservice.Entity.RoomRate;
import com.example.receptionservice.Enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRateRepository
        extends JpaRepository<RoomRate, Long> {

    Optional<RoomRate> findByRoomType(RoomType roomType);
}
