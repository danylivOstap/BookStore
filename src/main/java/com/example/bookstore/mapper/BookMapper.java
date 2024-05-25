package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategories;
import com.example.bookstore.dto.book.BookRequestDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {
    @Mapping(target = "categories", ignore = true)
    Book toModel(BookRequestDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, BookRequestDto requestDto) {
        book.setCategories(requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto dto, Book book) {
        dto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    BookDtoWithoutCategories toDtoWithoutCategories(Book book);

    @Named("bookById")
    default Book bookFromId(Long id) {
        return new Book(id);
    }
}
