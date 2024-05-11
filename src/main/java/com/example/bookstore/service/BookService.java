package com.example.bookstore.service;

import com.example.bookstore.dto.request.BookRequestDto;
import com.example.bookstore.dto.response.BookDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> findAll(Pageable pageable);

    BookDto save(BookRequestDto requestDto);

    BookDto getById(Long id);

    BookDto update(Long id, BookRequestDto requestDto);

    void deleteById(Long id);
}
