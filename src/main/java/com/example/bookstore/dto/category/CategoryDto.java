package com.example.bookstore.dto.category;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public record CategoryDto(
        Long id,
        String name,
        String description
) {

}
