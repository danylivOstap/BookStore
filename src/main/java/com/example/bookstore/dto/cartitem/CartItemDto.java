package com.example.bookstore.dto.cartitem;

public record CartItemDto(
        Long id,
        Long shoppingCartId,
        Long bookId,
        int quantity
) {

}
