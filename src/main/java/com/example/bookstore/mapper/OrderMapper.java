package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.security.mapper.UserMapper;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemIds", ignore = true)
    OrderDto toDto(Order order);

    @AfterMapping
    default void setOrderItemIds(@MappingTarget OrderDto orderDto, Order order) {
        orderDto.setOrderItemIds(order.getOrderItems().stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet())
        );
    }
}
