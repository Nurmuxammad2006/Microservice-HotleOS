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

    public RoomVacatedListener(
            CleaningTaskRepository cleaningTaskRepository,
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        this.cleaningTaskRepository = cleaningTaskRepository;
    }

    @RabbitListener(
            queues = RabbitMQConfig.ROOM_VACATED_HOUSEKEEPING_QUEUE
    )
    public void receive(String message) {

        System.out.println(
                "ROOM VACATED MESSAGE RECEIVED: " + message
        );

        try {
            RoomVacatedEvent event =
                    objectMapper.readValue(
                            message,
                            RoomVacatedEvent.class
                    );

            System.out.println(
                    "PARSED ROOM NUMBER: " + event.getRoomNumber()
            );

            CleaningTask task = new CleaningTask();
            task.setRoomNumber(event.getRoomNumber());
            task.setStatus(CleaningTaskStatus.PENDING);
            task.setCreatedAt(LocalDateTime.now());

            cleaningTaskRepository.save(task);

            System.out.println(
                    "CLEANING TASK CREATED FOR ROOM: "
                            + event.getRoomNumber()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Failed to process message"
            );
        }
    }
}