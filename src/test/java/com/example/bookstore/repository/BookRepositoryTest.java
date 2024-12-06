package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) throws SQLException {
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/add-three-default-books.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/add-three-default-categories.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/add-categories-for-books.sql"));
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/remove-all-books.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/remove-all-categories.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                  new ClassPathResource("database/books/remove-all-books-categories.sql"));
        }
    }

    @Test
    @DisplayName("Find all books by category")
    void findAllByCategory_CategoryExists_returnsBook() {
        //Given
        long categoryId = 2L;
        Book expected = new Book()
                .setId(3L)
                .setAuthor("Joan Rolling")
                .setTitle("Harry Potter 1")
                .setIsbn("111113")
                .setDescription("Cooly")
                .setPrice(BigDecimal.valueOf(324.99));

        //When
        List<Book> actual = bookRepository.findAllByCategory(categoryId);

        //Then
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(expected.getTitle(), actual.get(0).getTitle());
    }
}
