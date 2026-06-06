package com.example.receptionservice.Entity;

import com.example.receptionservice.Enums.GuestStayStatus;
import com.example.receptionservice.Enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "guest_stays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestStay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "nightly_rate")
    private BigDecimal nightlyRate;

    @Column(name = "number_of_nights")
    private Integer numberOfNights;

    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;

    @Column(name = "total_bill")
    private BigDecimal totalBill;

    @Enumerated(EnumType.STRING)
    private GuestStayStatus status;
}