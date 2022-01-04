package com.dwibagus.postcollab.controller;


import com.dwibagus.postcollab.kafka.KafkaConsumer;
import com.dwibagus.postcollab.kafka.KafkaProducer;
import com.dwibagus.postcollab.model.*;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.response.CommonResponseGenerator;
import com.dwibagus.postcollab.service.CategoryService;
import com.dwibagus.postcollab.service.CommentService;
import com.dwibagus.postcollab.service.LikesService;
import com.dwibagus.postcollab.service.PostService;
import com.dwibagus.postcollab.vo.likes.ResponseLikesTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final LikesService likesService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

//    kafka
    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

//    For Post
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        try {
            if (postService.create(post) == null){
                return new ResponseEntity<>(commonResponseGenerator.response(null, "please check your input", 400), HttpStatus.BAD_REQUEST);
            }
            return new  ResponseEntity<>(commonResponseGenerator.response(postService.create(post), "create post success", 201), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original")
    public ResponseEntity<?> getAllPost(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getAllPost(), "get all post success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not create post", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.uploadFile(file), "upload success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not upload image", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.findById(id), "get post success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable String id, @RequestBody Post post){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.editById(id, post), "edit post success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.deleteById(id), "delete post success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> findCommentById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.findCommentById(id), "get post with comment success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<?> findLikesById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.findLikesById(id), "get post with likes success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    //    Post Template
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getPostWithUserById(id), "get post with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPostWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getPostWithUser(), "get post with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", 400), HttpStatus.BAD_REQUEST);
        }
    }


//    for category
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.createCategory(category), "create category success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.getAllCategory(), "get all category success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no category", 400), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByIdCategory(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.findByIdCategory(id), "get category success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no category with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> editCategory(@PathVariable String id, @RequestBody Category category){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.editCategory(id, category), "edit category success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.deleteCategory(id), "delete category success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no category with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

//    comment
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody Comment comment){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.createComment(comment), "create comment success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original/comment")
    public ResponseEntity<?> getComment(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getComment(), "get all comment success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no comment", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentById(id), "get comment by id success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no comment with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable String id, @RequestBody Comment comment){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.updateCommentById(id, comment), "update comment success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.deleteCommentById(id), "delete comment success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no comment with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    //    Comment Template
    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getCommentWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentWithUserById(id), "get likes with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no comment with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentWithUser(), "get likes with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

// Likes
    @PostMapping("/likes")
    public ResponseEntity<?> createLikes(@RequestBody Likes likes){
        try {
            ResponseLikesTemplate responseLikesTemplate = likesService.createLikes(likes);
            if (responseLikesTemplate == null){
                return new ResponseEntity<>(commonResponseGenerator.response(null, "Post already like by user", 400), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(commonResponseGenerator.response(responseLikesTemplate, "create likes success", 201), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original/likes")
    public ResponseEntity<?> getAllLikes(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getAllLikes(), "get all likes success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/original/likes/{id}")
    public ResponseEntity<?> getLikesById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesById(id), "get likes by id success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/likes/{id}")
    public ResponseEntity<?> deleteLikesById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.deleteLikesById(id), "delete likes success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }



//    Likes Template
    @GetMapping("/likes/{id}")
    public ResponseEntity<?> getLikesWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesWithUserById(id), "get likes with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes with id " + id, 404), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/likes")
    public ResponseEntity<?> getLikesWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesWithUser(), "get likes with user success", 200));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/send")
    public void send(@RequestBody String data) {
        producer.produce(data);
    }

    @GetMapping("/receive")
    public List<String> receive() {
        return KafkaConsumer.messages;
    }

    public KafkaConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public KafkaProducer getProducer() {
        return producer;
    }

    public void setProducer(KafkaProducer producer) {
        this.producer = producer;
    }

}
