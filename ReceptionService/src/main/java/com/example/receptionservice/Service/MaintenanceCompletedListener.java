package com.example.receptionservice.Service;

import com.example.receptionservice.Config.RabbitMQConfig;
import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.dto.MaintenanceCompletedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class MaintenanceCompletedListener {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RoomRepository roomRepository;

    public  MaintenanceCompletedListener(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, RoomRepository roomRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.roomRepository = roomRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.MAINTENANCE_COMPLETED_QUEUE)
    public void receive(String message) {
        try {
            MaintenanceCompletedEvent event = objectMapper.readValue(message, MaintenanceCompletedEvent.class);

            Room room = roomRepository.findByRoomNumber(event.getRoomNumber()).
                    orElseThrow(() -> new RuntimeException("Room not found"));

            if (room.getStatus() == RoomStatus.MAINTENANCE) {
                room.setStatus(RoomStatus.AVAILABLE);
                room.setLastCleaned(LocalDateTime.now());
                roomRepository.save(room);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process maintenance completed event");
        }
    }
}
