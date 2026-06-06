package com.example.roomservice.Controller;

import com.example.roomservice.Entity.RoomServiceOrder;
import com.example.roomservice.Service.RoomService;
import com.example.roomservice.dto.CreateOrderRequest;
import com.example.roomservice.dto.OrderResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/room-service")
public class RoomServiceController {
    private final RoomService roomService;

    public RoomServiceController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return roomService.createOrder(request);
    }

    @GetMapping("/orders")
    public List<RoomServiceOrder> getOrders() {
        return roomService.getOrders();
    }

    @PutMapping("/orders/{orderId}/prepare")
    public OrderResponse prepareOrder(@PathVariable Long orderId) {
        return roomService.prepareOrder(
                orderId
        );
    }

    @PutMapping("/orders/{orderId}/deliver")
    public OrderResponse sendForDelivery(@PathVariable Long orderId) {
        return roomService.sendForDelivery(
                orderId
        );
    }

    @PutMapping("/orders/{orderId}/complete")
    public OrderResponse completeOrder(@PathVariable Long orderId) {
        return roomService.completeOrder(
                orderId
        );
    }

    @GetMapping("/rooms/{roomNumber}/total")
    public BigDecimal getRoomServiceTotal(
            @PathVariable Integer roomNumber
    ) {
        return roomService.getRoomServiceTotal(
                roomNumber
        );
    }
}
