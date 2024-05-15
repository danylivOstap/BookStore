package com.example.bookstore.service.impl;

import com.example.bookstore.dto.request.BookRequestDto;
import com.example.bookstore.dto.response.BookDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
            .map(bookMapper::toDto)
            .toList();
    }

    @Override
    public BookDto save(final BookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public BookDto getById(final Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Can't find book By id: %s".formatted(id))));
    }

    @Override
    public BookDto update(final Long id, final BookRequestDto requestDto) {
        final Book book = bookMapper.toModel(requestDto);
        book.setId(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book By id: %s".formatted(id)))
                .getId());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(final Long id) {
        bookRepository.deleteById(id);
    }
}
