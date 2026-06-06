package com.example.housekeepingservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCleaningTaskRequest {

    private Integer roomNumber;
}
