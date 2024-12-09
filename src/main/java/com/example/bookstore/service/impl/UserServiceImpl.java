package com.example.bookstore.service.impl;

import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;
import com.example.bookstore.security.mapper.UserMapper;
import com.example.bookstore.security.model.User;
import com.example.bookstore.security.repository.UserRepository;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto register(final UserCreationRequestDto userCreationRequestDto) {
        final User user = userMapper.toModel(userCreationRequestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
