package com.example.receptionservice.Entity;

import com.example.receptionservice.Enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "room_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", unique = true)
    private RoomType roomType;

    @Column(name = "nightly_rate")
    private BigDecimal nightlyRate;
}