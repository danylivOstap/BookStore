package com.example.bookstore.service.impl;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CategoryRequestDto;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return new PageImpl<>(categoryRepository.findAll(pageable).stream()
            .map(categoryMapper::toDto)
            .toList());
    }

    @Override
    public CategoryDto getById(final Long id) {
        return categoryMapper.toDto(getCategoryById(id));
    }

    private Category getCategoryById(final Long id) {
        return categoryRepository.findById(id).orElseThrow(
            EntityNotFoundException::new);
    }

    @Override
    public CategoryDto save(final CategoryRequestDto requestDto) {
        final Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(final Long id, final CategoryRequestDto requestDto) {
        final Category categoryFromDb = getCategoryById(id);
        final Category category = categoryMapper.toModel(requestDto);
        category.setId(categoryFromDb.getId());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(final Long id) {
        categoryRepository.deleteById(id);
    }
}
