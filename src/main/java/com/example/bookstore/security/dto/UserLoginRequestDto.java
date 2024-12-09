package com.example.bookstore.security.dto;

import com.example.bookstore.validation.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Email
        @NotBlank
        @Size(max = 100)
        String email,
        @NotBlank
        @Size(max = 100)
        String password) {
}
