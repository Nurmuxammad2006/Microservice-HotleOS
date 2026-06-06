package com.example.maintenanceservice.dto;

import com.example.maintenanceservice.Enums.IssueCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceRequest {

    private Integer roomNumber;
    private IssueCode issueCode;
    private String description;
}
