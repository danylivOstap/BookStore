package com.example.bookstore.dto.cart;

import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<Long> cartItemsId
) {
    public ShoppingCartDto setCartItemIds(Set<Long> cartItemsId) {
        return new ShoppingCartDto(this.id, this.userId, cartItemsId);
    }
}
