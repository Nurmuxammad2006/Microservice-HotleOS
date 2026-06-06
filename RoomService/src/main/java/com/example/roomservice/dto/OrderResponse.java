package com.example.roomservice.dto;

import com.example.roomservice.Enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Integer roomNumber;
    private String itemName;
    private Integer quantity;;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String message;
}
