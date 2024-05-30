package com.example.bookstore.dto.order;

import com.example.bookstore.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus orderStatus;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private Set<Long> orderItemIds;
}
