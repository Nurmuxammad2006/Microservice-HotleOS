package com.example.housekeepingservice.Repository;

import com.example.housekeepingservice.Entity.CleaningTask;
import com.example.housekeepingservice.Enums.CleaningTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CleaningTaskRepository extends JpaRepository<CleaningTask, Long> {

    List<CleaningTask> findByStatus(CleaningTaskStatus status);

    Optional<CleaningTask> findByRoomNumberOrderByCreatedAtDesc(Integer roomNumber);
}
