package com.example.bookstore.exception;

import com.example.bookstore.dto.response.ErrorResponseDto;
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
            "Input arguments validation failure", HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleAllExceptions(Exception e) {
        return generateExceptionDetails("An unexpected error occurred",
            HttpStatus.INTERNAL_SERVER_ERROR, List.of(e.getMessage()));
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
