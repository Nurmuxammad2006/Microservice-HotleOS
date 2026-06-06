package com.example.housekeepingservice.Service;

import com.example.housekeepingservice.Config.RabbitMQConfig;
import com.example.housekeepingservice.Entity.CleaningTask;
import com.example.housekeepingservice.Enums.CleaningTaskStatus;
import com.example.housekeepingservice.Repository.CleaningTaskRepository;
import com.example.housekeepingservice.dto.RoomVacatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class RoomVacatedListener {

    private final CleaningTaskRepository cleaningTaskRepository;
    private final ObjectMapper objectMapper;

    public RoomVacatedListener(CleaningTaskRepository cleaningTaskRepository,
                               ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.cleaningTaskRepository = cleaningTaskRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.ROOM_VACATED_QUEUE)
    public void receive(String message) {

        try {
            RoomVacatedEvent event = objectMapper.readValue(message, RoomVacatedEvent.class);

            CleaningTask task = new CleaningTask();
            task.setRoomNumber(event.getRoomNumber());
            task.setStatus(CleaningTaskStatus.PENDING);
            task.setCreatedAt(LocalDateTime.now());

            cleaningTaskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process message");
        }
    }
}
