package com.example.roomservice.Service;

import com.example.roomservice.Config.RabbitMQConfig;
import com.example.roomservice.Enums.OrderStatus;
import com.example.roomservice.dto.OrderStatusEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderStatusPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    public OrderStatusPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(OrderStatusEvent event) {
        try {

            String json =
                    objectMapper.writeValueAsString(
                            event
                    );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig
                            .ROOM_SERVICE_STATUS_QUEUE,
                    json
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish order status");
        }
    }

}
