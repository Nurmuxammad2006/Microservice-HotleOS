package com.example.maintenanceservice.Service;

import com.example.maintenanceservice.Config.RabbitMQConfig;
import com.example.maintenanceservice.dto.MaintenanceCompletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class MaintenanceCompletedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MaintenanceCompletedPublisher(RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Integer roomNumber) {

        try {

            String json = objectMapper.writeValueAsString(new MaintenanceCompletedEvent(roomNumber));

            rabbitTemplate.convertAndSend(RabbitMQConfig.MAINTENANCE_COMPLETED_QUEUE,json);

        } catch (Exception e) {

            throw new RuntimeException("Failed to publish maintenance completed event");
        }
    }
}
