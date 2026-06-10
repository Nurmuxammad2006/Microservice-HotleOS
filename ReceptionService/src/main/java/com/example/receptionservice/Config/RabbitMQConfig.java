package com.example.receptionservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

    public static final String ROOM_VACATED_QUEUE = "room-vacated-queue";
    public static final String ROOM_CLEANED_QUEUE = "room-cleaned-queue";
    public static final String MAINTENANCE_CREATED_QUEUE = "maintenance-created-queue";
    public static final String MAINTENANCE_COMPLETED_QUEUE = "maintenance-completed-queue";
    public static final String ROOM_VACATED_HOUSEKEEPING_QUEUE ="room-vacated-housekeeping-queue";
    public static final String ROOM_VACATED_DASHBOARD_QUEUE ="room-vacated-dashboard-queue";

    @Bean
    public Queue roomVacatedHousekeepingQueue() {
        return new Queue(ROOM_VACATED_HOUSEKEEPING_QUEUE, true);
    }

    @Bean
    public Queue roomVacatedDashboardQueue() {
        return new Queue(ROOM_VACATED_DASHBOARD_QUEUE, true);
    }

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
