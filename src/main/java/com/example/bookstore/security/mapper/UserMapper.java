package com.example.bookstore.security.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.security.dto.UserDto;
import com.example.bookstore.security.model.User;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserCreationRequestDto requestDto);

    UserDto toDto(User user);

    @Named("userById")
    default User userById(Long id) {
        return Optional.ofNullable(id)
            .map(User::new)
            .orElse(null);
    }
}
