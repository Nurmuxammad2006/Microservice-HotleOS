package com.example.receptionservice.Service;

import com.example.receptionservice.Config.RabbitMQConfig;
import com.example.receptionservice.dto.RoomVacatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RoomVacatedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RoomVacatedPublisher(
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishRoomVacated(Integer roomNumber) {
        try {
            RoomVacatedEvent event =
                    new RoomVacatedEvent(roomNumber);

            String json =
                    objectMapper.writeValueAsString(event);

            System.out.println(
                    "PUBLISHING ROOM VACATED: " + json
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ROOM_VACATED_HOUSEKEEPING_QUEUE,
                    json
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ROOM_VACATED_DASHBOARD_QUEUE,
                    json
            );

            System.out.println(
                    "MESSAGE SENT TO HOUSEKEEPING AND DASHBOARD"
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to publish room vacated event"
            );
        }
    }
}