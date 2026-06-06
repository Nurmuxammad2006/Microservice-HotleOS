package com.example.housekeepingservice.Controller;

import com.example.housekeepingservice.Entity.CleaningTask;
import com.example.housekeepingservice.Service.HouseKeepingService;
import com.example.housekeepingservice.dto.CleaningTaskResponse;
import com.example.housekeepingservice.dto.CreateCleaningTaskRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/housekeeping")
public class HouseKeepingController {

    private final HouseKeepingService houseKeepingService;
    public HouseKeepingController(HouseKeepingService houseKeepingService) {
        this.houseKeepingService = houseKeepingService;
    }

//    @PostMapping("/tasks")
//    public CleaningTaskResponse createTask(@RequestBody CreateCleaningTaskRequest request) {
//        return houseKeepingService.createTask(request);
//    }

    @GetMapping("/tasks")
    public List<CleaningTask> getTasks() {
        return houseKeepingService.getTasks();
    }

    @PutMapping("/tasks/{roomNumber}/complete")
    public CleaningTaskResponse completeTask(@PathVariable Integer roomNumber) {
        return houseKeepingService.completeTask(roomNumber);
    }

    @PutMapping("/tasks/{roomNumber}/start")
    public CleaningTaskResponse startTask(@PathVariable Integer roomNumber) {
        return houseKeepingService.startTask(roomNumber);
    }
}
