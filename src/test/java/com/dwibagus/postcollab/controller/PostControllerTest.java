package com.dwibagus.postcollab.controller;

import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.response.CommonResponseGenerator;
import com.dwibagus.postcollab.service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    CommonResponseGenerator commonResponseGenerator;
    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postController)
                .build();
    }

    @Test
    public void addOne_WillReturnOk() throws Exception {
        //Given

        Post post = new Post();
        post.setCategoryId("1");
        post.setUserId(1L);
        post.setName("dwi");
        post.setFile("file");

        String jsonBody = gson.toJson(post);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/post")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.create(post)).thenReturn(post);


        //When
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //Then
        verify(postService, times(1)).create(post);

        String jsonBodyExpect = gson.toJson(commonResponseGenerator.response(post, "success", "200"));
        assertEquals(jsonBodyExpect, response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

}