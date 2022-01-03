package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.repository.CommentRepository;
import com.dwibagus.postcollab.repository.LikesRepository;
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

class LikesServiceImplTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private String id;

    @InjectMocks
    private LikesServiceImpl service;
    @Mock
    private LikesRepository likesRepository;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(String.class);
    }

    @Test
    public void getOne_WillReturnProductOutput() {
        // Given
        Likes likes = EASY_RANDOM.nextObject(Likes.class);

        when(likesRepository.findById(likes.getId())).thenReturn(Optional.of(likes));

        // When
        var result = service.getLikesById(likes.getId());

        // Then
        verify(likesRepository, times(1)).findById(likes.getId());
        assertEquals(likes, result);
    }

    @Test
    public void getAll_WillReturnListProductOutput() {
        Iterable<Likes> likesIterable = EASY_RANDOM.objects(Likes.class, 2)
                .collect(Collectors.toList());
        when(likesRepository.findAll()).thenReturn((List<Likes>) likesIterable);

        // When
        var result = service.getAllLikes();

        // Then
        List<Likes> outputs = new ArrayList<>();
        for (Likes likes: likesIterable) {
            outputs.add(likes);
        }
        verify(likesRepository, times(1)).findAll();
        assertEquals(outputs, result);
    }
}