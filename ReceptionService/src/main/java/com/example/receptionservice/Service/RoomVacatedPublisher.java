package com.example.receptionservice.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoomVacatedPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RoomVacatedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishRoomVacated(Integer roomNumber) {

        System.out.println("PUBLISHING ROOM VACATED: " + roomNumber);

        rabbitTemplate.convertAndSend(
                "room-vacated-queue",
                "Room " + roomNumber + " has been vacated"
        );

        System.out.println("MESSAGE SENT");
    }
}