package com.example.receptionservice.Repository;

import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomTypeAndStatus(
            RoomType roomType,
            RoomStatus status
    );

    Optional<Room> findByRoomNumber(Integer roomNumber);
}