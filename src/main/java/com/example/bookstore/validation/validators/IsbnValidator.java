package com.example.bookstore.validation.validators;

import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.validation.Isbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private final BookRepository bookRepository;

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        return bookRepository.findByIsbn(isbn).isEmpty();
    }
}
