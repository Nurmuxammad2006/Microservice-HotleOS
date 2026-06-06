package com.example.receptionservice.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
}