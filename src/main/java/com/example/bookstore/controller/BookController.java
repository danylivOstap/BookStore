package com.example.bookstore.controller;

import com.example.bookstore.dto.request.BookRequestDto;
import com.example.bookstore.dto.response.BookDto;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Book management", description = "Endpoints for managing books")
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all Books", description = "Get a list of all available Books")
    public List<BookDto> getAll(final Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Book by it's id", description = "Get a book by it's id")
    public BookDto getBookById(@PathVariable final Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new Book", description = "Create a new Book")
    public BookDto createBook(@RequestBody @Valid final BookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "update a Book", description = "Update a book by it's id")
    public BookDto updateBook(@PathVariable final Long id,
            @RequestBody @Valid final BookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a Book", description = "Delete a Book by it's id")
    public ResponseEntity<Object> deleteBookById(@PathVariable final Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>("Book by id " + id + " was successfully deleted",
            HttpStatus.NO_CONTENT);
    }
}
