package com.example.maintenanceservice.dto;

import com.example.maintenanceservice.Enums.IssueCode;
import com.example.maintenanceservice.Enums.MaintenancePriority;
import com.example.maintenanceservice.Enums.MaintenanceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestResponse {

    private Integer roomNumber;

    private IssueCode issueCode;

    private MaintenancePriority priority;

    private MaintenanceStatus status;

    private String message;
}
