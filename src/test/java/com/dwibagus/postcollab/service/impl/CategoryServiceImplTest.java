package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.repository.CategoryRepository;
import com.dwibagus.postcollab.repository.CommentRepository;
import org.antlr.stringtemplate.language.Cat;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CategoryServiceImplTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private String id;

    @InjectMocks
    private CategoryServiceImpl service;
    @Mock
    private CategoryRepository categoryRepository;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(String.class);
    }

    @Test
    public void getOne_WillReturnProductOutput() {
        // Given
        Category category = EASY_RANDOM.nextObject(Category.class);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        // When
        var result = service.findByIdCategory(category.getId());

        // Then
        verify(categoryRepository, times(1)).findById(category.getId());
        assertEquals(category, result);
    }

    @Test
    public void getAll_WillReturnListProductOutput() {
        Iterable<Category> categoryIterable = EASY_RANDOM.objects(Category.class, 2)
                .collect(Collectors.toList());
        when(categoryRepository.findAll()).thenReturn((List<Category>) categoryIterable);

        // When
        var result = service.getAllCategory();

        // Then
        List<Category> outputs = new ArrayList<>();
        for (Category category: categoryIterable) {
            outputs.add(category);
        }
        verify(categoryRepository, times(1)).findAll();
        assertEquals(outputs, result);
    }
}