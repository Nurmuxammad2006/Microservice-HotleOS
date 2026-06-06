package com.example.receptionservice.Service;

import com.example.receptionservice.Config.RabbitMQConfig;
import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.dto.MaintenanceCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class MaintenanceCreatedListener {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RoomRepository roomRepository;

    public MaintenanceCreatedListener(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, RoomRepository roomRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.roomRepository = roomRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.MAINTENANCE_CREATED_QUEUE)
    public void receive(String message) {
        try {
            MaintenanceCreatedEvent event = objectMapper.readValue(message, MaintenanceCreatedEvent.class);

            Room room = roomRepository.findByRoomNumber(event.getRoomNumber()).orElseThrow(
                    () -> new RuntimeException("Room not found")
            );

            room.setStatus(RoomStatus.MAINTENANCE);
            roomRepository.save(room);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process maintenance created event");
        }
    }
}
