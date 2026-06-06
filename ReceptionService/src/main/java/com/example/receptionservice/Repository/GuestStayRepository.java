package com.example.receptionservice.Repository;

import com.example.receptionservice.Entity.GuestStay;
import com.example.receptionservice.Enums.GuestStayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestStayRepository extends JpaRepository<GuestStay, Long> {
    List<GuestStay> findByStatus(GuestStayStatus status);

    Optional<GuestStay> findByRoomNumberAndStatus(Integer roomNumber, GuestStayStatus status
    );

    Optional<GuestStay> findByGuestNameAndStatus(
            String guestName,
            GuestStayStatus status
    );
}