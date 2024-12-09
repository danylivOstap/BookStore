package com.example.bookstore.security.dto;

import com.example.bookstore.validation.Email;
import com.example.bookstore.validation.RepeatedPasswordMatch;
import com.example.bookstore.validation.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RepeatedPasswordMatch
public record UserCreationRequestDto(
        @UniqueEmail
        @Email
        String email,
        @Size(max = 100)
        @NotBlank
        @Size(max = 100)
        String password,
        @NotBlank
        @Size(max = 100)
        String repeatedPassword,
        @NotBlank
        @Size(max = 100)
        String firstName,
        @NotBlank
        @Size(max = 100)
        String lastName,
        @Size(max = 255)
        String shippingAddress
) {

}
