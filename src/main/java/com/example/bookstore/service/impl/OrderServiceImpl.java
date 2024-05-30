package com.example.bookstore.service.impl;

import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.OrderItemMapper;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.enums.OrderStatus;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.security.model.User;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderDto createOrder(final String username, final String shippingAddress) {
        final User user = userService.findByEmail(username);
        final ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart.getCartItems() == null || shoppingCart.getCartItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }
        final Order order = mapCartToOrder(shippingAddress, shoppingCart);
        final Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderDto> getAllOrdersForUser(final String email, final Pageable pageable) {
        return new PageImpl<>(orderRepository.findAllByUser(email, pageable).stream()
                .map(orderMapper::toDto)
                .toList());
    }

    @Override
    public Page<OrderItemDto> getOrderItems(final Long orderId, final Pageable pageable) {
        return new PageImpl<>(orderItemRepository.findAllByOrderId(orderId, pageable).stream()
            .map(orderItemMapper::toDto)
            .toList());
    }

    @Override
    public OrderItemDto getOrderItemById(final Long orderId, final Long itemId,
                final Pageable pageable) {
        final Order order = getOrder(orderId);
        final OrderItem orderItem = getParticularItemFromOrder(itemId, order);
        return orderItemMapper.toDto(orderItem);
    }

    private OrderItem getParticularItemFromOrder(final Long itemId, final Order order) {
        return order.getOrderItems().stream()
            .filter((item) -> item.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(
                "Can't find order item by id: '%s'".formatted(itemId)));
    }

    @Override
    public OrderDto updateOrderStatus(final Long id, final OrderStatus status) {
        final Order order = getOrder(id);
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order getOrder(final Long id) {
        return orderRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Can't find order by id: '%s'".formatted(id)));
    }

    private Order mapCartToOrder(final String shippingAddress, final ShoppingCart shoppingCart) {
        final Order order = new Order();
        order.setShippingAddress(shippingAddress);
        final BigDecimal totalItemsSum = getTotalItemsSum(shoppingCart);
        order.setTotal(totalItemsSum);
        order.setOrderItems(getAllOrderItems(shoppingCart, order));
        return order;
    }

    private Set<OrderItem> getAllOrderItems(final ShoppingCart shoppingCart, final Order order) {
        return shoppingCart.getCartItems().stream()
              .map(cartItem -> mapCartItemToOrderItem(cartItem, order))
              .collect(Collectors.toSet());
    }

    private OrderItem mapCartItemToOrderItem(final CartItem cartItem, final Order order) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setOrder(order);
        return orderItem;
    }

    private BigDecimal multiplyBookPriceByQuantity(final CartItem cartItem) {
        return cartItem.getBook().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
    }

    private BigDecimal getTotalItemsSum(final ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
            .map(calculatePriceForOneBook())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Function<CartItem, BigDecimal> calculatePriceForOneBook() {
        return this::multiplyBookPriceByQuantity;
    }
}
