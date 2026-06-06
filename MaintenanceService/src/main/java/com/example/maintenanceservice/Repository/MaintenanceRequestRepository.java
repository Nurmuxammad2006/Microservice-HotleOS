package com.example.maintenanceservice.Repository;

import com.example.maintenanceservice.Entity.MaintenanceIssueType;
import com.example.maintenanceservice.Entity.MaintenanceRequest;
import com.example.maintenanceservice.Enums.IssueCode;
import com.example.maintenanceservice.Enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {

    List<MaintenanceRequest> findByStatus(MaintenanceStatus status);

    Optional<MaintenanceRequest> findTopByRoomNumberOrderByCreatedAtDesc(Integer roomNumber);

}
