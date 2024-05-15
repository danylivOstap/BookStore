package com.example.bookstore.dto.response;

import java.util.Map;

public record ErrorResponseDto(
        Map<String, Object> details) {
}
