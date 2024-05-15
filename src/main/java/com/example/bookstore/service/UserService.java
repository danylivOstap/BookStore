package com.example.bookstore.service;

import com.example.bookstore.dto.request.UserCreationRequestDto;
import com.example.bookstore.dto.response.UserDto;

public interface UserService {

    UserDto register(UserCreationRequestDto userCreationRequestDto);
}
