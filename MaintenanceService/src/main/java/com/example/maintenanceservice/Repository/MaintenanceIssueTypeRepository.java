package com.example.maintenanceservice.Repository;

import com.example.maintenanceservice.Entity.MaintenanceIssueType;
import com.example.maintenanceservice.Enums.IssueCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaintenanceIssueTypeRepository extends JpaRepository<MaintenanceIssueType, Long> {

    Optional<MaintenanceIssueType> findByIssueCode(IssueCode issueCode);

}
