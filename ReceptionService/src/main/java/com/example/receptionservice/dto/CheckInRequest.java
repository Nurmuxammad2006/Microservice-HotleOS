package com.example.receptionservice.dto;

import com.example.receptionservice.Enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInRequest {

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotNull(message = "Room type is required")
    private RoomType roomType;
    private Integer preferredFloor;
    private Boolean nearElevator;
    private Boolean nearStairs;

    @Min(value = 1, message = "Number of nights must be at least 1")
    private Integer numberOfNights;
}
