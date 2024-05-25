package com.example.bookstore.service.impl;

import com.example.bookstore.exception.EntityNotFoundException;
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find user by email: '%s'".formatted(email)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean isUserRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
