package com.example.bookstore.validation.validators;

import com.example.bookstore.security.dto.UserCreationRequestDto;
import com.example.bookstore.validation.RepeatedPasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RepeatedPasswordValidator
        implements ConstraintValidator<RepeatedPasswordMatch, UserCreationRequestDto> {

    @Override
    public boolean isValid(UserCreationRequestDto userDto, ConstraintValidatorContext context) {
        return userDto.password().equals(userDto.repeatedPassword());
    }
}
