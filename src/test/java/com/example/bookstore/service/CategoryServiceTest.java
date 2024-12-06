package com.example.bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CategoryRequestDto;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.impl.CategoryServiceImpl;
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
public class CategoryServiceTest {

    public static final String FANTASY_CATEGORY_NAME = "Fantasy";
    public static final String FANTASY_CATEGORY_DESCRIPTION =
            "Literature set in an imaginary universe";
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify category is saved correctly")
    public void save_validCategory_ShouldSaveCorrectly() {
        //Given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                FANTASY_CATEGORY_NAME, FANTASY_CATEGORY_DESCRIPTION);
        Category category = new Category()
                .setId(1L)
                .setDescription(categoryRequestDto.description())
                .setName(categoryRequestDto.name());
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getDescription(),
                category.getName());

        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        Mockito.when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);

        //When
        CategoryDto savedCategoryDto = categoryService.save(categoryRequestDto);

        //Then
        assertThat(savedCategoryDto).isEqualTo(categoryDto);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).toDto(category);
        Mockito.verify(categoryMapper, Mockito.times(1))
                .toModel(categoryRequestDto);
        Mockito.verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Verify findAll() method works")
    public void findAll_ValidPageable_returnsAllCategories() {
        //Given
        Category category = new Category()
                .setName(FANTASY_CATEGORY_NAME)
                .setId(1L)
                .setDescription(FANTASY_CATEGORY_DESCRIPTION);
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getDescription(),
                category.getName());

        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = List.of(category);
        Page<Category> expextedPage = new PageImpl<>(categories, pageable, categories.size());

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(expextedPage);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        //When
        Page<CategoryDto> actualCategories = categoryService.findAll(pageable);

        //Then
        assertThat(actualCategories.getContent().size()).isEqualTo(1);
        assertThat(actualCategories.stream().toList().get(0)).isEqualTo(categoryDto);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .findAll(pageable);
        Mockito.verify(categoryMapper, Mockito.times(1)).toDto(category);
        Mockito.verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Verify the correct category is returned by id")
    public void getCategoryById_WithValidId_ShouldReturnValidTitle() {
        //Given
        long categoryId = 1L;
        Category category = new Category()
                .setName(FANTASY_CATEGORY_NAME)
                .setId(1L)
                .setDescription(FANTASY_CATEGORY_DESCRIPTION);
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription());

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(Optional.of(category).get())).thenReturn(categoryDto);

        //When
        String actual = categoryService.getById(categoryId).name();

        //Then
        String expected = category.getName();

        Assertions.assertEquals(actual, expected);
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoryMapper, Mockito.times(1)).toDto(category);

        Mockito.verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }
}
