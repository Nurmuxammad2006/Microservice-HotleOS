package com.example.receptionservice.dto;

import com.example.receptionservice.Enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomStatusResponse {
    private Integer roomNumber;
    private RoomStatus status;
}
