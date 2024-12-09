package com.example.bookstore.security.service;

import com.example.bookstore.security.JwtUtil;
import com.example.bookstore.security.dto.UserLoginRequestDto;
import com.example.bookstore.security.dto.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(final UserLoginRequestDto userLoginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginRequestDto.email(), userLoginRequestDto.password()));
        String token = jwtUtil.generateToken(userLoginRequestDto.email());
        return new UserLoginResponseDto(token);
    }
}
