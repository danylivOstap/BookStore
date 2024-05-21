package com.example.bookstore.security.service;

import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.security.JwtUtil;
import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;
import com.example.bookstore.security.dto.UserLoginRequestDto;
import com.example.bookstore.security.dto.UserLoginResponseDto;
import com.example.bookstore.security.mapper.UserMapper;
import com.example.bookstore.security.model.Role.RoleName;
import com.example.bookstore.security.model.User;
import com.example.bookstore.security.repository.RoleRepository;
import com.example.bookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserLoginResponseDto authenticate(final UserLoginRequestDto userLoginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginRequestDto.email(), userLoginRequestDto.password()));
        String token = jwtUtil.generateToken(userLoginRequestDto.email());
        return new UserLoginResponseDto(token);
    }

    public UserDto register(final UserCreationRequestDto userCreationRequestDto) {
        if (userService.isUserRegistered(userCreationRequestDto.email())) {
            throw new RegistrationException("Can't register user");
        }
        final User user = userMapper.toModel(userCreationRequestDto);
        user.setPassword(passwordEncoder.encode(userCreationRequestDto.password()));
        user.setRoles(Set.of(roleRepository.findByName(RoleName.USER).orElseThrow(
                    () -> new RegistrationException("Can't register user"))));
        return userMapper.toDto(userService.save(user));
    }
}
