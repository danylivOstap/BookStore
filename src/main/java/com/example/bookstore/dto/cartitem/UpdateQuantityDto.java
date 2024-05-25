package com.example.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;

public record UpdateQuantityDto(
        @Min(1)
        int quantity
) {

}
