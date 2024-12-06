package com.example.bookstore.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookRequestDto;
import com.example.bookstore.model.response.PageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource) throws SQLException {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
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
    @DisplayName("Create a new book")
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void createBook_ValidRequestDto_Success() throws Exception {
        //Given
        BookRequestDto requestDto = new BookRequestDto()
                .setAuthor("Mark Twain")
                .setIsbn("1214123111")
                .setTitle("Tom Sawyer")
                .setDescription("Cool")
                .setPrice(BigDecimal.valueOf(224.9))
                .setCategoryIds(new HashSet<>(List.of(1L)));

        BookDto expected = new BookDto()
                .setAuthor(requestDto.getAuthor())
                .setTitle(requestDto.getTitle())
                .setIsbn(requestDto.getIsbn())
                .setDescription(requestDto.getDescription())
                .setPrice(requestDto.getPrice())
                .setCategoryIds(new HashSet<>(List.of(1L)));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        //When
        MvcResult result = mockMvc.perform(post("/api/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //Then
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get all books")
    void getAll_GivenBooks_ShouldReturnAllBooks() throws Exception {
        //Given
        List<BookDto> expected = new ArrayList<>();
        expected.add(new BookDto()
                .setId(1L)
                .setAuthor("Bahryanyi")
                .setTitle("Tyhrolovy")
                .setIsbn("111111")
                .setDescription("Cool")
                .setPrice(BigDecimal.valueOf(224.99))
        );
        expected.add(new BookDto()
                .setId(2L)
                .setAuthor("Bahryanyi")
                .setTitle("Sad Hetsymanskyi")
                .setIsbn("111112")
                .setDescription("Coollll")
                .setPrice(BigDecimal.valueOf(124.99))
        );
        expected.add(new BookDto()
                .setId(3L)
                .setAuthor("Joan Rolling")
                .setTitle("Harry Potter 1")
                .setIsbn("111113")
                .setDescription("Cooly")
                .setPrice(BigDecimal.valueOf(324.99))
        );

        //When
        MvcResult result = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //Then
        PageResponse<BookDto> booksPage = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                new TypeReference<PageResponse<BookDto>>() {
            });
        List<BookDto> actual = booksPage.getContent();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        Assertions.assertEquals(expected.get(1).getTitle(), actual.get(1).getTitle());
        Assertions.assertEquals(expected.get(2).getTitle(), actual.get(2).getTitle());
    }
}
