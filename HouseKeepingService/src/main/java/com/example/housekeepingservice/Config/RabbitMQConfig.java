package com.example.housekeepingservice.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

    public static final String ROOM_VACATED_QUEUE =
            "room-vacated-queue";
    public static final String ROOM_CLEANED_QUEUE =
            "room-cleaned-queue";

    @Bean
    public Queue roomVacatedQueue() {
        return new Queue(
                ROOM_VACATED_QUEUE,
                true
        );
    }

    @Bean
    public Queue roomCleanedQueue() {
        return new Queue(
                ROOM_CLEANED_QUEUE,
                true
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}