package com.example.bookstore.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.example.bookstore.dto.ErrorResponseDto;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MESSAGE = "Message";
    private static final String STATUS_CODE = "Status code";
    private static final String ERRORS = "Errors";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidationException(
            MethodArgumentNotValidException e) {
        final List<String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return generateExceptionDetails(
            "Input arguments validation failure", BAD_REQUEST, errors);
    }

    @ExceptionHandler(RegistrationException.class)
    public ErrorResponseDto handleRegistrationException(RegistrationException e) {
        return generateExceptionDetails("Registration error occurred", BAD_REQUEST,
            List.of());
    }

    @ExceptionHandler(CartAlreadyContainsItem.class)
    public ErrorResponseDto handleCartAlreadyContainsItem(CartAlreadyContainsItem e) {
        return generateExceptionDetails("Cart already contains this item", BAD_REQUEST,
            List.of(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleAllExceptions(Exception e) {
        return generateExceptionDetails("An unexpected error occurred",
            INTERNAL_SERVER_ERROR, List.of());
    }

    private ErrorResponseDto generateExceptionDetails(
            String message,
            HttpStatus status,
            List<String> errors) {
        Map<String, Object> exceptionDetails = new LinkedHashMap<>();
        exceptionDetails.put(MESSAGE, message);
        exceptionDetails.put(STATUS_CODE, status);
        exceptionDetails.put(ERRORS, errors);
        return new ErrorResponseDto(exceptionDetails);
    }
}
