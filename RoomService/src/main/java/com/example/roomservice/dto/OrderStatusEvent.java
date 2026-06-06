package com.example.roomservice.dto;

import com.example.roomservice.Enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusEvent {

    private Long orderId;
    private Integer roomNumber;
    private OrderStatus status;
}
