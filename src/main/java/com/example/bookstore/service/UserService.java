package com.example.bookstore.service;

import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;

public interface UserService {

    UserDto register(UserCreationRequestDto userCreationRequestDto);
}
