package com.example.receptionservice.Entity;

import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", unique = true)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(name = "near_elevator")
    private Boolean nearElevator;

    @Column(name = "last_cleaned")
    private LocalDateTime lastCleaned;
}