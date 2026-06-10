package com.example.housekeepingservice.Service;

import com.example.housekeepingservice.Entity.CleaningTask;
import com.example.housekeepingservice.Enums.CleaningTaskStatus;
import com.example.housekeepingservice.Repository.CleaningTaskRepository;
import com.example.housekeepingservice.dto.CleaningTaskResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseKeepingService {

    private final RoomCleanedPublisher roomCleanedPublisher;
    private final CleaningTaskRepository cleaningTaskRepository;

    public HouseKeepingService(
            CleaningTaskRepository cleaningTaskRepository,
            RoomCleanedPublisher roomCleanedPublisher
    ) {
        this.cleaningTaskRepository = cleaningTaskRepository;
        this.roomCleanedPublisher = roomCleanedPublisher;
    }

    public List<CleaningTask> getTasks() {
        return cleaningTaskRepository.findAll();
    }

    public CleaningTaskResponse startTask(Integer roomNumber) {

        CleaningTask task =
                cleaningTaskRepository
                        .findTopByRoomNumberOrderByCreatedAtDesc(roomNumber)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found for room " + roomNumber
                                )
                        );

        if (task.getStatus() != CleaningTaskStatus.PENDING) {
            throw new RuntimeException(
                    "Task must be in PENDING status"
            );
        }

        task.setStatus(CleaningTaskStatus.CLEANING);

        cleaningTaskRepository.save(task);

        return new CleaningTaskResponse(
                task.getId(),
                task.getRoomNumber(),
                task.getStatus(),
                "Cleaning started"
        );
    }

    public CleaningTaskResponse completeTask(Integer roomNumber) {

        CleaningTask cleaningTask =
                cleaningTaskRepository
                        .findTopByRoomNumberOrderByCreatedAtDesc(roomNumber)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found for room " + roomNumber
                                )
                        );

        if (cleaningTask.getStatus() != CleaningTaskStatus.CLEANING) {
            throw new RuntimeException(
                    "Task must be in CLEANING status"
            );
        }

        cleaningTask.setStatus(CleaningTaskStatus.COMPLETED);
        cleaningTask.setCompletedAt(LocalDateTime.now());

        cleaningTaskRepository.save(cleaningTask);

        roomCleanedPublisher.publishRoomCleaned(
                cleaningTask.getRoomNumber()
        );

        return new CleaningTaskResponse(
                cleaningTask.getId(),
                cleaningTask.getRoomNumber(),
                cleaningTask.getStatus(),
                "Room is cleaned"
        );
    }
}