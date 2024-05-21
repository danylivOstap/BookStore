package com.example.bookstore.security.controller;

import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;
import com.example.bookstore.security.dto.UserLoginRequestDto;
import com.example.bookstore.security.dto.UserLoginResponseDto;
import com.example.bookstore.security.service.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Create user", description = "Create a new user")
    public UserDto register(@RequestBody @Valid UserCreationRequestDto userCreationRequestDto) {
        return authenticationService.register(userCreationRequestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
