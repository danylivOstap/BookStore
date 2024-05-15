package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.request.UserCreationRequestDto;
import com.example.bookstore.dto.response.UserDto;
import com.example.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserCreationRequestDto requestDto);

    UserDto toDto(User user);
}
