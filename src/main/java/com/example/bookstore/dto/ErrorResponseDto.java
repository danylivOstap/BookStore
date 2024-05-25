package com.example.bookstore.dto;

import java.util.Map;

public record ErrorResponseDto(
        Map<String, Object> details) {
}
