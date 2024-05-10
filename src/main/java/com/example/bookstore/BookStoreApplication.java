package com.example.bookstore;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    private final BookService bookService;

    public BookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Book book = new Book();
            book.setAuthor("Joan Rolling");
            book.setTitle("Harry Potter");
            book.setIsbn("1234561213");
            book.setPrice(new BigDecimal("45"));
            bookService.save(book);

            for (Book b : bookService.findAll()) {
                System.out.println(b);
            }
        };
    }
}
