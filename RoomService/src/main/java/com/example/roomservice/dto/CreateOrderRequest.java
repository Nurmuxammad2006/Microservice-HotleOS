package com.example.roomservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private Integer roomNumber;
    private Long menuItemId;
    private Integer quantity;
}
