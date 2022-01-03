package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.kafka.KafkaConsumer;
import com.dwibagus.postcollab.kafka.KafkaProducer;
import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.CommentRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.CommentService;
import com.dwibagus.postcollab.vo.ResponseCommentTemplate;
import com.dwibagus.postcollab.vo.ResponseLikesTemplate;
import com.dwibagus.postcollab.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceIml implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaConsumer consumer;

    @Override
    public Comment createComment(Comment comment){
        Post post = postRepository.findById(comment.getPostId()).get();
        User user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + comment.getUserId(), User.class);
        if (post != null && user != null){
            post.setTotalComment(post.getTotalComment()+1);
            post.setUpdated_at(new Date());
            postRepository.save(post);
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComment(){
        List<String> messages = consumer.messages;
        for (int i = 0; i < messages.size(); i++){
            System.out.println(messages.get(i));
        }
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(String id){
        return commentRepository.findById(id).orElseThrow(() -> {
            throw  new RuntimeException("Not Found");
        });
    }


    @Override
    public Comment updateCommentById(String id, Comment comment){
        Comment comment1 = commentRepository.findById(id).get();
        if (comment.getDescription() != null){
            comment1.setDescription(comment.getDescription());
            comment1.setUpdated_at(new Date());
        }
        comment1.setUpdated_at(new Date());
        commentRepository.save(comment1);
        return comment1;
    }

    @Override
    public Comment deleteCommentById(String id){
        Comment comment = commentRepository.findById(id).get();
        Post post = postRepository.findById(comment.getPostId()).get();
        if (post != null){
            post.setTotalComment(post.getTotalComment()-1);
            post.setUpdated_at(new Date());
            postRepository.save(post);
        }
        commentRepository.deleteById(id);
        return comment;
    }


    @Override
    public ResponseCommentTemplate getCommentWithUserById(String id){
        ResponseCommentTemplate responseCommentTemplate = new ResponseCommentTemplate();
        Comment comment = commentRepository.findById(id).get();

        User user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + comment.getUserId(), User.class);

        responseCommentTemplate.setDescription(comment.getDescription());
        responseCommentTemplate.setCreated_at(comment.getCreated_at());
        responseCommentTemplate.setUpdated_at(comment.getUpdated_at());
        responseCommentTemplate.setPost(postRepository.findById(comment.getPostId()).get());
        responseCommentTemplate.setUser(user);
        return responseCommentTemplate;
    }

    @Override
    public List<ResponseCommentTemplate> getCommentWithUser(){
        List<ResponseCommentTemplate> allCommentTemplate = new ArrayList<>();
        ResponseCommentTemplate responseCommentTemplate = new ResponseCommentTemplate();

        User user = new User();
        List<Comment> allComment = commentRepository.findAll();
        for (int i = 0; i < allComment.size(); i++){
            user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + allComment.get(i).getUserId(), User.class);
            responseCommentTemplate.setDescription(allComment.get(i).getDescription());
            responseCommentTemplate.setCreated_at(allComment.get(i).getCreated_at());
            responseCommentTemplate.setUpdated_at(allComment.get(i).getUpdated_at());
            responseCommentTemplate.setPost(postRepository.findById(allComment.get(i).getPostId()).get());
            responseCommentTemplate.setUser(user);

            allCommentTemplate.add(responseCommentTemplate);
        }
        return allCommentTemplate;
    }
}
