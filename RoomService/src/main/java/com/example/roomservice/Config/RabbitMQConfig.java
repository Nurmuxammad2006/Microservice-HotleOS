package com.example.roomservice.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

    public static final String ROOM_SERVICE_STATUS_QUEUE = "room-service-status-queue";

    @Bean
    public Queue roomServiceStatusQueue() {
        return new Queue(ROOM_SERVICE_STATUS_QUEUE, true);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
