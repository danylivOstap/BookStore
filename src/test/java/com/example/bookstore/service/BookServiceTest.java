package com.example.bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    public static final String BOOK_AUTHOR_BAHRYANYI = "Bahryanyi";
    public static final String BOOK_TITLE_TYHROLOVY = "Tyhrolovy";
    public static final String ISBN = "12123141";
    public static final double PRICE = 224.5;
    public static final String BOOK_DESCRIPTION = "Cool";
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify the correct book is returned by id")
    public void getBookTitle_WithValidId_ShouldReturnValidTitle() {
        //Given
        Long bookId = 1L;
        Book book = new Book()
                .setAuthor(BOOK_AUTHOR_BAHRYANYI)
                .setTitle(BOOK_TITLE_TYHROLOVY)
                .setId(bookId)
                .setIsbn(ISBN)
                .setPrice(BigDecimal.valueOf(PRICE))
                .setDescription(BOOK_DESCRIPTION);

        BookDto dto = new BookDto()
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setPrice(book.getPrice())
                .setDescription(book.getDescription());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(Optional.of(book).get())).thenReturn(dto);

        //When
        String actual = bookService.getById(bookId).getTitle();

        //Then
        String expected = book.getTitle();

        Assertions.assertEquals(actual, expected);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
        Mockito.verify(bookMapper, Mockito.times(1)).toDto(book);

        Mockito.verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Verify findAllWithCategories() method works")
    public void findAllWithCategories_ValidPageable_returnsAllBooks() {
        //Given
        Long bookId = 1L;
        Book book = new Book()
                .setAuthor(BOOK_AUTHOR_BAHRYANYI)
                .setTitle(BOOK_TITLE_TYHROLOVY)
                .setId(bookId)
                .setIsbn(ISBN)
                .setPrice(BigDecimal.valueOf(PRICE))
                .setDescription(BOOK_DESCRIPTION);

        BookDto dto = new BookDto()
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setPrice(book.getPrice())
                .setDescription(book.getDescription());

        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(book);
        Page<Book> expextedPage = new PageImpl<>(books, pageable, books.size());

        Mockito.when(bookRepository.findAllWithCategories(pageable)).thenReturn(expextedPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        //When
        Page<BookDto> actualBooks = bookService.findAll(pageable);

        //Then
        assertThat(actualBooks.getContent().size()).isEqualTo(1);
        assertThat(actualBooks.stream().toList().get(0)).isEqualTo(dto);

        Mockito.verify(bookRepository, Mockito.times(1))
                .findAllWithCategories(pageable);
        Mockito.verify(bookMapper, Mockito.times(1)).toDto(book);
        Mockito.verifyNoMoreInteractions(bookRepository, bookMapper);
    }
}
