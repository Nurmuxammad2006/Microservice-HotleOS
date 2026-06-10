package com.example.receptionservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutResponse {
    private String guestName;
    private Integer roomNumber;
    private BigDecimal totalBill;
    private String message;
}