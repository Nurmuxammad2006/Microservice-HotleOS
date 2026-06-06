package com.example.housekeepingservice.dto;

import com.example.housekeepingservice.Enums.CleaningTaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleaningTaskResponse {

    private Long taskId;
    private Integer roomNumber;
    private CleaningTaskStatus status;
    private String message;
}
