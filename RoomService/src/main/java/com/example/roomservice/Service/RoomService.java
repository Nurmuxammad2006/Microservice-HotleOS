package com.example.roomservice.Service;

import com.example.roomservice.Config.RestTemplateConfig;
import com.example.roomservice.Entity.MenuItem;
import com.example.roomservice.Entity.RoomServiceOrder;
import com.example.roomservice.Enums.OrderStatus;
import com.example.roomservice.Repository.MenuItemRepository;
import com.example.roomservice.Repository.RoomServiceOrderRepository;
import com.example.roomservice.dto.CreateOrderRequest;
import com.example.roomservice.dto.OrderResponse;
import com.example.roomservice.dto.OrderStatusEvent;
import com.example.roomservice.dto.RoomStatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    private final RoomServiceOrderRepository roomServiceOrderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderStatusPublisher orderStatusPublisher;
    private final RestTemplateConfig  restTemplateConfig;
    private final RestTemplate restTemplate;
    public RoomService(RoomServiceOrderRepository roomServiceOrderRepository,
                       MenuItemRepository menuItemRepository,
                       OrderStatusPublisher orderStatusPublisher,
                       RestTemplateConfig restTemplateConfig,
                       RestTemplate restTemplate) {
        this.roomServiceOrderRepository = roomServiceOrderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderStatusPublisher = orderStatusPublisher;
        this.restTemplateConfig = restTemplateConfig;
        this.restTemplate = restTemplate;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        if (!isRoomOccupied(
                request.getRoomNumber()
        )) {

            throw new RuntimeException(
                    "Only occupied rooms can order room service"
            );
        }

        MenuItem menuItem =
                menuItemRepository
                        .findById(request.getMenuItemId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Menu item not found"
                                )
                        );

        BigDecimal totalPrice =
                menuItem.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        RoomServiceOrder order =
                new RoomServiceOrder();

        order.setRoomNumber(
                request.getRoomNumber()
        );

        order.setMenuItem(
                menuItem
        );

        order.setQuantity(
                request.getQuantity()
        );

        order.setTotalPrice(
                totalPrice
        );

        order.setStatus(
                OrderStatus.RECEIVED
        );

        order.setBilled(
                false
        );

        order.setCreatedAt(
                LocalDateTime.now()
        );

        roomServiceOrderRepository.save(
                order
        );

        orderStatusPublisher.publish(
                new OrderStatusEvent(
                        order.getId(),
                        order.getRoomNumber(),
                        order.getStatus()
                )
        );

        return new OrderResponse(
                order.getId(),
                order.getRoomNumber(),
                menuItem.getItemName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                "Order created"
        );
    }

    public List<RoomServiceOrder> getOrders() {
        return roomServiceOrderRepository.findAll();
    }

    public OrderResponse prepareOrder(Long orderId) {

        RoomServiceOrder order =
                roomServiceOrderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found"
                                )
                        );

        order.setStatus(
                OrderStatus.PREPARING
        );

        roomServiceOrderRepository.save(
                order
        );

        orderStatusPublisher.publish(
                new OrderStatusEvent(
                        order.getId(),
                        order.getRoomNumber(),
                        order.getStatus()
                )
        );

        return buildResponse(
                order,
                "Order is preparing"
        );
    }

    public OrderResponse sendForDelivery(Long orderId) {

        RoomServiceOrder order =
                roomServiceOrderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found"
                                )
                        );

        order.setStatus(
                OrderStatus.OUT_FOR_DELIVERY
        );

        roomServiceOrderRepository.save(
                order
        );

        orderStatusPublisher.publish(
                new OrderStatusEvent(
                        order.getId(),
                        order.getRoomNumber(),
                        order.getStatus()
                )
        );

        return buildResponse(
                order,
                "Order out for delivery"
        );
    }

    public OrderResponse completeOrder(Long orderId) {

        RoomServiceOrder order =
                roomServiceOrderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found"
                                )
                        );

        order.setStatus(
                OrderStatus.DELIVERED
        );

        roomServiceOrderRepository.save(
                order
        );

        orderStatusPublisher.publish(
                new OrderStatusEvent(
                        order.getId(),
                        order.getRoomNumber(),
                        order.getStatus()
                )
        );

        return buildResponse(
                order,
                "Order delivered"
        );
    }

    private OrderResponse buildResponse(RoomServiceOrder order, String message) {

        return new OrderResponse(
                order.getId(),
                order.getRoomNumber(),
                order.getMenuItem().getItemName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                message
        );
    }

    private boolean isRoomOccupied(
            Integer roomNumber
    ) {

        String url =
                "http://localhost:8080/reception/rooms/"
                        + roomNumber
                        + "/status";

        RoomStatusResponse response =
                restTemplate.getForObject(
                        url,
                        RoomStatusResponse.class
                );

        return response != null
                && "OCCUPIED".equals(
                response.getStatus()
        );
    }

    public BigDecimal getRoomServiceTotal(
            Integer roomNumber
    ) {

        return roomServiceOrderRepository
                .findByRoomNumberAndBilledFalse(
                        roomNumber
                )
                .stream()
                .filter(order ->
                        order.getStatus()
                                == OrderStatus.DELIVERED
                )
                .map(RoomServiceOrder::getTotalPrice)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}
