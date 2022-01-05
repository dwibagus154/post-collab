package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.CommentRepository;
import com.dwibagus.postcollab.repository.PostRepository;
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

class CommentServiceImlTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private String id;

    @InjectMocks
    private CommentServiceIml service;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(String.class);
    }

    @Test
    public void getOne_WillReturnProductOutput() {
        // Given
        Comment comment = EASY_RANDOM.nextObject(Comment.class);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        // When
        var result = service.getCommentById(comment.getId());

        // Then
        verify(commentRepository, times(1)).findById(comment.getId());
        assertEquals(comment, result);
    }

    @Test
    public void getAll_WillReturnListProductOutput() {
        Iterable<Comment> commentIterable = EASY_RANDOM.objects(Comment.class, 2)
                .collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn((List<Comment>) commentIterable);

        // When
        var result = service.getComment();

        // Then
        List<Comment> outputs = new ArrayList<>();
        for (Comment comment: commentIterable) {
            outputs.add(comment);
        }
        verify(commentRepository, times(1)).findAll();
        assertEquals(outputs, result);
    }

    @Test
    public void deleteOne_WillDoNothing() {
        // Given
        Comment comment = EASY_RANDOM.nextObject(Comment.class);
        Post post = EASY_RANDOM.nextObject(Post.class);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(postRepository.findById(comment.getPostId())).thenReturn(Optional.of(post));
        doNothing().when(commentRepository).deleteById(comment.getId());

        // When
        var result = service.deleteCommentById(comment.getId());

        // Then
        verify(commentRepository, times(1)).deleteById(comment.getId());
        assertEquals(comment, result);
    }

}