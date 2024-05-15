package com.example.bookstore.service.impl;

import com.example.bookstore.dto.request.UserCreationRequestDto;
import com.example.bookstore.dto.response.UserDto;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
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
