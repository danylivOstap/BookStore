package com.example.bookstore.dto.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ShoppingCartRequestDto(
        @NotNull
        Long userId,
        @NotEmpty
        Set<Long> cartItemIds
) {

}
