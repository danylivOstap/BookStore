package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategories;
import com.example.bookstore.dto.book.BookRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> findAll(Pageable pageable);

    BookDto save(BookRequestDto requestDto);

    BookDto getById(Long id);

    BookDto update(Long id, BookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategories> getBooksByCategory(Long categoryId);
}
