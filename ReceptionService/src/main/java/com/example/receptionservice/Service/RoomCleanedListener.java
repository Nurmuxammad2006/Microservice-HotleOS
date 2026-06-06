package com.example.receptionservice.Service;

import com.example.receptionservice.Config.RabbitMQConfig;
import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.dto.RoomCleanedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class RoomCleanedListener {

    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    public RoomCleanedListener(RoomRepository roomRepository, ObjectMapper objectMapper) {
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(
            queues = RabbitMQConfig.ROOM_CLEANED_QUEUE
    )
    public void receive(String message) {
        try {
            RoomCleanedEvent event = objectMapper.readValue(message, RoomCleanedEvent.class);

            Room room = roomRepository.findByRoomNumber(event.getRoomNumber())
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            if (room.getStatus() == RoomStatus.DIRTY) {
                room.setStatus(RoomStatus.AVAILABLE);

                room.setLastCleaned(LocalDateTime.now());
            }
            room.setLastCleaned(LocalDateTime.now());
            roomRepository.save(room);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process room cleaned event");
        }
    }
}
