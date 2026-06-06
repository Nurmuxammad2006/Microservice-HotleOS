package com.example.maintenanceservice.Entity;

import com.example.maintenanceservice.Enums.IssueCode;
import com.example.maintenanceservice.Enums.MaintenancePriority;
import com.example.maintenanceservice.Enums.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private IssueCode issueCode;

    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenancePriority priority;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
