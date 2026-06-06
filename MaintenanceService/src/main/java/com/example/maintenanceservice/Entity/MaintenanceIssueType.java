package com.example.maintenanceservice.Entity;

import com.example.maintenanceservice.Enums.IssueCode;
import com.example.maintenanceservice.Enums.MaintenancePriority;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "maintenance_issue_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceIssueType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IssueCode issueCode;

    private String displayName;

    @Enumerated(EnumType.STRING)
    private MaintenancePriority priority;

}
