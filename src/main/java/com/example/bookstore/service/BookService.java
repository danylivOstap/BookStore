package com.example.bookstore.service;

import com.example.bookstore.dto.request.BookRequestDto;
import com.example.bookstore.dto.response.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> findAll();

    BookDto save(BookRequestDto requestDto);

    BookDto getById(Long id);
}
