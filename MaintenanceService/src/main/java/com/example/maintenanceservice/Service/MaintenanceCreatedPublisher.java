package com.example.maintenanceservice.Service;

import com.example.maintenanceservice.Config.RabbitMQConfig;
import com.example.maintenanceservice.dto.MaintenanceCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class MaintenanceCreatedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    public MaintenanceCreatedPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Integer roomNumber) {
        try {
            String json = objectMapper.writeValueAsString(new MaintenanceCreatedEvent(roomNumber));

            rabbitTemplate.convertAndSend(RabbitMQConfig.MAINTENANCE_CREATED_QUEUE, json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish maintenance created event");
        }
    }
}
