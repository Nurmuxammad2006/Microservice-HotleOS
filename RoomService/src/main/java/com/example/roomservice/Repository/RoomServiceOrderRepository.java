package com.example.roomservice.Repository;

import com.example.roomservice.Entity.RoomServiceOrder;
import com.example.roomservice.Enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomServiceOrderRepository extends JpaRepository<RoomServiceOrder, Long> {

    List<RoomServiceOrder> findByRoomNumber(Integer roomNumber);
    List<RoomServiceOrder> findByRoomNumberAndStatus(Integer roomNumber, OrderStatus status);
    List<RoomServiceOrder> findByRoomNumberAndBilledFalse(Integer roomNumber);
}
