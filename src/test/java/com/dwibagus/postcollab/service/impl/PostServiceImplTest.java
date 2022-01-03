package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.PostRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PostServiceImplTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private Long id;

    @InjectMocks
    private PostServiceImpl service;
    @Mock
    private PostRepository postRepository;
    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(Long.class);
    }

    @Test
    public void addOne_WillReturnProductOutput(){
        // given
        Post post = EASY_RANDOM.nextObject(Post.class);
        when(postRepository.save(post)).thenReturn(post);
        //when

        var result = service.create(post);
        //then
        verify(postRepository, times(1)).save(post);
    }
}