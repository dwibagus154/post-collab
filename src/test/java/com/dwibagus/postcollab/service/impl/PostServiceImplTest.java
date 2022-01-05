package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.vo.post.ResponseTemplateVO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private String id;

    @InjectMocks
    private PostServiceImpl service;
    @Mock
    private PostRepository postRepository;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(String.class);
    }

//    @Test
//    public void addOne_WillReturnProductOutput(){
//        // given
//        Post post = new Post();
//        ResponseTemplateVO responseTemplateVO = EASY_RANDOM.nextObject(ResponseTemplateVO.class);
//        post.setId(responseTemplateVO.getId());
//        post.setName(responseTemplateVO.getName());
//        post.setTotalComment(responseTemplateVO.getTotalComment());
//        post.setFile(responseTemplateVO.getFile().getId());
//        post.setUserId(responseTemplateVO.getUser().getId());
//        post.setCategoryId(responseTemplateVO.getCategory().getId());
//        post.setCreated_at(responseTemplateVO.getCreated_at());
//        post.setUpdated_at(responseTemplateVO.getUpdated_at());
//        post.setTotalLikes(responseTemplateVO.getTotalLikes());
//        when(postRepository.save(post)).thenReturn(post);
//        //when
//
//        var result = service.create(post);
//        //then
//        verify(postRepository, times(1)).save(post);
//    }

//    @Test
//    public void getOne_WillReturnNull() {
//        // Given
//        when(postRepository.findById(id)).thenReturn(null);
//
//        // When
//        var result = service.findById(id);
//
//        // Then
//        verify(postRepository, times(1)).findById(id);
//        assertNull(result);
//    }

    @Test
    public void getOne_WillReturnProductOutput() {
        // Given
        Post post = EASY_RANDOM.nextObject(Post.class);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // When
        var result = service.findById(post.getId());

        // Then
        verify(postRepository, times(1)).findById(post.getId());
        assertEquals(post, result);
    }

    @Test
    public void getAll_WillReturnListProductOutput() {
        Iterable<Post> postIterable = EASY_RANDOM.objects(Post.class, 2)
                .collect(Collectors.toList());
        when(postRepository.findAll()).thenReturn((List<Post>) postIterable);

        // When
        var result = service.getAllPost();

        // Then
        List<Post> outputs = new ArrayList<>();
        for (Post post: postIterable) {
            outputs.add(post);
        }
        verify(postRepository, times(1)).findAll();
        assertEquals(outputs, result);
    }

}