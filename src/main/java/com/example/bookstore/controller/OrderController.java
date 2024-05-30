package com.example.bookstore.controller;

import com.example.bookstore.dto.order.CreateOrderRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.model.enums.OrderStatus;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoints to manage orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Endpoint to create orders")
    public OrderDto createOrder(
            @AuthenticationPrincipal final UserDetails userDetails,
            @RequestBody @Valid final CreateOrderRequestDto requestDto) {
        return orderService.createOrder(userDetails.getUsername(), requestDto.shippingAddress());
    }

    @GetMapping
    @Operation(summary = "Endpoint to get all users orders")
    public Page<OrderDto> getAllOrders(
                @AuthenticationPrincipal final UserDetails userDetails,
                final Pageable pageable) {
        return orderService.getAllOrdersForUser(userDetails.getUsername(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Endpoint to get specific orders items")
    public Page<OrderItemDto> getOrderItems(@PathVariable final Long orderId, Pageable pageable) {
        return orderService.getOrderItems(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Endpoint to get specific order item")
    public OrderItemDto getOrderItems(
            @PathVariable final Long orderId,
            @PathVariable final Long itemId,
            final Pageable pageable) {
        return orderService.getOrderItemById(orderId, itemId, pageable);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Endpoint to update orders status")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto updateStatus(
            @PathVariable final Long id,
            @RequestParam final OrderStatus orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }
}
