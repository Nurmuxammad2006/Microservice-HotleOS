package com.example.receptionservice.dto;

import com.example.receptionservice.Enums.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInRequest {

    private String guestName;
    private RoomType roomType;
    private Integer preferredFloor;
    private Boolean nearElevator;
    private Integer numberOfNights;
}
