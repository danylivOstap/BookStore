package com.example.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemDto(
        @NotNull
        Long bookId,
        @Min(1)
        int quantity
) {

}
