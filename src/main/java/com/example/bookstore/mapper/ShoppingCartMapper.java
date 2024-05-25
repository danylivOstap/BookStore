package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.cart.ShoppingCartDto;
import com.example.bookstore.dto.cart.ShoppingCartRequestDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.security.mapper.UserMapper;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "user", source = "userId", qualifiedByName = "userById")
    @Mapping(target = "cartItems", ignore = true)
    ShoppingCart toModel(ShoppingCartRequestDto requestDto);

    @AfterMapping
    default void setCarItems(
            @MappingTarget ShoppingCart shoppingCart, ShoppingCartRequestDto requestDto) {
        shoppingCart.setCartItems(requestDto.cartItemIds().stream()
                .map(CartItem::new)
                .collect(Collectors.toSet()));
    }

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItemsId", ignore = true)
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setCartItemIds(
            @MappingTarget ShoppingCartDto shoppingCartDto, ShoppingCart shoppingCart) {
        shoppingCartDto.setCartItemsId(shoppingCart.getCartItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet()));
    }

    @Named("shoppingCartById")
    default ShoppingCart shoppingCartById(Long id) {
        return Optional.ofNullable(id)
                .map(ShoppingCart::new)
                .orElse(null);
    }
}
