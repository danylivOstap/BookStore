package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto createOrder(String username, String shippingAddress);

    Page<OrderDto> getAllOrdersForUser(String username, Pageable pageable);

    Page<OrderItemDto> getOrderItems(Long orderId, Pageable pageable);

    OrderItemDto getOrderItemById(Long orderId, Long itemId, Pageable pageable);

    OrderDto updateOrderStatus(Long id, OrderStatus orderStatus);
}
