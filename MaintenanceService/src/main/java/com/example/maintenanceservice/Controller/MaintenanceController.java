package com.example.maintenanceservice.Controller;

import com.example.maintenanceservice.Service.MaintenanceService;
import com.example.maintenanceservice.dto.CreateMaintenanceRequest;
import com.example.maintenanceservice.dto.MaintenanceRequestResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/report")
    public MaintenanceRequestResponse createReport(@RequestBody CreateMaintenanceRequest request) {
        return maintenanceService.createReport(request);
    }

    @PutMapping("/rooms/{roomNumber}/start")
    public MaintenanceRequestResponse startMaintenance(@PathVariable Integer roomNumber){
        return maintenanceService.startMaintenance(roomNumber);
    }

    @PutMapping("/rooms/{roomNumber}/complete")
    public MaintenanceRequestResponse completeMaintenance(@PathVariable Integer roomNumber){
        return maintenanceService.completeMaintenance(roomNumber);
    }
}
