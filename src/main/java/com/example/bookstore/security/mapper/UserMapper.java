package com.example.bookstore.security.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;
import com.example.bookstore.security.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserCreationRequestDto requestDto);

    UserDto toDto(User user);
}
