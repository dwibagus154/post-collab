package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.kafka.KafkaConsumer;
import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.CommentRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.CommentService;
import com.dwibagus.postcollab.vo.comment.ResponseCommentTemplate;
import com.dwibagus.postcollab.vo.object.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${uriAuth}")
    private String uriAuth;

    @Override
    public List<Comment> getComment(){

        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(String id){
        return commentRepository.findById(id).get();
    }

    @Override
    public ResponseCommentTemplate createComment(Comment comment){
        Post post = postRepository.findById(comment.getPostId()).get();
        User user = restTemplate.getForObject(this.uriAuth + comment.getUserId(), User.class);
        // check if user already activated
        if (!user.isActive()){
            return null;
        }
        // check if there is user and post
        if (post != null && user != null){
            post.setTotalComment(post.getTotalComment()+1);
            post.setUpdated_at(new Date());
            postRepository.save(post);
        }
        commentRepository.save(comment);
        return this.getCommentWithUserById(comment.getId());
    }

    @Override
    public ResponseCommentTemplate updateCommentById(String id, Comment comment){
        Comment comment1 = commentRepository.findById(id).get();
        if (comment.getDescription() != null){
            comment1.setDescription(comment.getDescription());
            comment1.setUpdated_at(new Date());
        }
        comment1.setUpdated_at(new Date());
        commentRepository.save(comment1);
        return this.getCommentWithUserById(id);
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

        User user = restTemplate.getForObject(this.uriAuth + comment.getUserId(), User.class);

        responseCommentTemplate.setId(comment.getId());
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

//        delete comment if post deleted
        List<String> messages = consumer.messages;
        if (messages.size() > 0){
            for (int i = 0; i < allComment.size(); i++) {
                for (int j = 0; j < messages.size(); j++) {
                    if (allComment.get(i).getPostId().equals(messages.get(j))) {
                        commentRepository.deleteById(allComment.get(i).getId());
                    }
                }
            }
        }
        for (int i = 0; i < allComment.size(); i++){
            user = restTemplate.getForObject(this.uriAuth + allComment.get(i).getUserId(), User.class);
            responseCommentTemplate.setId(allComment.get(i).getId());
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
