package com.example.housekeepingservice.Service;

import com.example.housekeepingservice.Config.RabbitMQConfig;
import com.example.housekeepingservice.dto.RoomCleanedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RoomCleanedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RoomCleanedPublisher(
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishRoomCleaned(Integer roomNumber) {
        try {
            RoomCleanedEvent event =
                    new RoomCleanedEvent(roomNumber);

            String json =
                    objectMapper.writeValueAsString(event);

            System.out.println(
                    "PUBLISHING ROOM CLEANED: " + json
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ROOM_CLEANED_RECEPTION_QUEUE,
                    json
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ROOM_CLEANED_DASHBOARD_QUEUE,
                    json
            );

            System.out.println(
                    "ROOM CLEANED MESSAGE SENT TO RECEPTION AND DASHBOARD"
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Failed to publish room cleaned event"
            );
        }
    }
}