package com.example.bookstore.controller;

import com.example.bookstore.dto.request.UserCreationRequestDto;
import com.example.bookstore.dto.response.UserDto;
import com.example.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication management", description = "Endpoints for authentication operations")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Create user", description = "Create a new user")
    public UserDto register(@RequestBody @Valid UserCreationRequestDto userCreationRequestDto) {
        return userService.register(userCreationRequestDto);
    }
}
