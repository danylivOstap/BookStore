package com.example.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 255)
        String description
) {

}
