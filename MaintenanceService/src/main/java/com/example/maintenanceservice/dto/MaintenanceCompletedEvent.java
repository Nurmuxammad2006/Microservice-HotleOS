package com.example.maintenanceservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCompletedEvent {

    private Integer roomNumber;
}