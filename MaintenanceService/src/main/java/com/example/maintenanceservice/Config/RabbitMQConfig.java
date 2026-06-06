package com.example.maintenanceservice.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MAINTENANCE_CREATED_QUEUE =
            "maintenance-created-queue";

    public static final String MAINTENANCE_COMPLETED_QUEUE =
            "maintenance-completed-queue";

    @Bean
    public Queue maintenanceCreatedQueue() {
        return new Queue(
                MAINTENANCE_CREATED_QUEUE,
                true
        );
    }

    @Bean
    public Queue maintenanceCompletedQueue() {
        return new Queue(
                MAINTENANCE_COMPLETED_QUEUE,
                true
        );
    }
}
