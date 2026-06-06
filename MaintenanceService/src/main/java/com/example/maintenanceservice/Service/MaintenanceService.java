package com.example.maintenanceservice.Service;

import com.example.maintenanceservice.Entity.MaintenanceIssueType;
import com.example.maintenanceservice.Entity.MaintenanceRequest;
import com.example.maintenanceservice.Enums.MaintenanceStatus;
import com.example.maintenanceservice.Repository.MaintenanceIssueTypeRepository;
import com.example.maintenanceservice.Repository.MaintenanceRequestRepository;
import com.example.maintenanceservice.dto.CreateMaintenanceRequest;
import com.example.maintenanceservice.dto.MaintenanceCompletedEvent;
import com.example.maintenanceservice.dto.MaintenanceRequestResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final MaintenanceIssueTypeRepository maintenanceIssueTypeRepository;
    private final MaintenanceCreatedPublisher maintenanceCreatedPublisher;
    private final MaintenanceCompletedPublisher maintenanceCompletedPublisher;

    public MaintenanceService(MaintenanceRequestRepository maintenanceRequestRepository,
                              MaintenanceIssueTypeRepository maintenanceIssueTypeRepository,
                              MaintenanceCreatedPublisher maintenanceCreatedPublisher,
                              MaintenanceCompletedPublisher maintenanceCompletedPublisher) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.maintenanceIssueTypeRepository = maintenanceIssueTypeRepository;
        this.maintenanceCreatedPublisher = maintenanceCreatedPublisher;
        this.maintenanceCompletedPublisher = maintenanceCompletedPublisher;
    }

    public MaintenanceRequestResponse createReport(CreateMaintenanceRequest request) {

        MaintenanceIssueType issueType =
                maintenanceIssueTypeRepository.findByIssueCode(request.getIssueCode())
                        .orElseThrow(() -> new RuntimeException("Maintenance Issue Type Not Found"));

        MaintenanceRequest  maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setRoomNumber(request.getRoomNumber());
        maintenanceRequest.setIssueCode(request.getIssueCode());
        maintenanceRequest.setDescription(request.getDescription());
        maintenanceRequest.setPriority(issueType.getPriority());
        maintenanceRequest.setStatus(MaintenanceStatus.PENDING);
        maintenanceRequest.setCreatedAt(LocalDateTime.now());
        maintenanceRequestRepository.save(maintenanceRequest);

        maintenanceCreatedPublisher.publish(
                request.getRoomNumber()
        );

        return new MaintenanceRequestResponse(
                maintenanceRequest.getRoomNumber(),
                maintenanceRequest.getIssueCode(),
                maintenanceRequest.getPriority(),
                maintenanceRequest.getStatus(),
                "Maintenance request created"
        );
    }

    public List<MaintenanceRequest> getRequests() {
        return maintenanceRequestRepository.findAll();
    }

    public MaintenanceRequestResponse startMaintenance(Integer roomNumber) {

        MaintenanceRequest request =
                maintenanceRequestRepository
                        .findTopByRoomNumberOrderByCreatedAtDesc(
                                roomNumber
                        ).orElseThrow(
                                () -> new RuntimeException(
                                        "Request not found"
                                )
                        );

        if (request.getStatus() != MaintenanceStatus.PENDING) {
            throw new RuntimeException("Request is not in PENDING status");
        }

        request.setStatus(MaintenanceStatus.IN_PROGRESS);

        maintenanceRequestRepository.save(request);

        return new MaintenanceRequestResponse(
                request.getRoomNumber(),
                request.getIssueCode(),
                request.getPriority(),
                request.getStatus(),
                "Maintenance started"
        );
    }

    public MaintenanceRequestResponse completeMaintenance(Integer roomNumber) {

        MaintenanceRequest request =
                maintenanceRequestRepository
                        .findTopByRoomNumberOrderByCreatedAtDesc(
                                roomNumber
                        ).orElseThrow(
                                () -> new RuntimeException(
                                        "Request not found"
                                )
                        );

        if (request.getStatus() != MaintenanceStatus.IN_PROGRESS) {
            throw new RuntimeException("Request must be IN_PROGRESS");
        }

        request.setStatus(MaintenanceStatus.COMPLETED);

        request.setCompletedAt(LocalDateTime.now());

        maintenanceRequestRepository.save(request);

        maintenanceCompletedPublisher.publish(
                roomNumber
        );

        return new MaintenanceRequestResponse(
                request.getRoomNumber(),
                request.getIssueCode(),
                request.getPriority(),
                request.getStatus(),
                "Maintenance completed"
        );
    }
}