package com.dwibagus.postcollab.controller;


import com.dwibagus.postcollab.model.*;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.response.CommonResponse;
import com.dwibagus.postcollab.response.CommonResponseGenerator;
import com.dwibagus.postcollab.service.CategoryService;
import com.dwibagus.postcollab.service.CommentService;
import com.dwibagus.postcollab.service.LikesService;
import com.dwibagus.postcollab.service.PostService;
import com.dwibagus.postcollab.vo.ResponseTemplateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

//    For Post
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.create(post), "create post success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not create post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPost(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getAllPost(), "get all post success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not create post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.uploadFile(file), "upload success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not upload image", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.findById(id), "get post success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable String id, @RequestBody Post post){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.editById(id, post), "edit post success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.deleteById(id), "delete post success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }



//    for category
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.createCategory(category), "create category success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "can not create category", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.getAllCategory(), "get all category success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no category", "400"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByIdCategory(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.findByIdCategory(id), "get category success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> editCategory(@PathVariable String id, @RequestBody Category category){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.editCategory(id, category), "edit category success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(categoryService.deleteCategory(id), "delete category success", "200"));
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

//    comment
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody Comment comment){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.createComment(comment), "create comment success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getComment(), "get all comment success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentById(id), "get comment by id success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable String id, @RequestBody Comment comment){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.updateCommentById(id, comment), "updatel comment success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.deleteCommentById(id), "delete comment success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

// Likes
    @PostMapping("/likes")
    public ResponseEntity<?> createLikes(@RequestBody Likes likes){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.createLikes(likes), "create likes success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/likes")
    public ResponseEntity<?> getAllLikes(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getAllLikes(), "get all likes success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/likes/{id}")
    public ResponseEntity<?> getLikesById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesById(id), "get likes by id success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/likes/{id}")
    public ResponseEntity<?> deleteLikesById(@PathVariable String id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.deleteLikesById(id), "delete likes success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }



//    Post Template
    @GetMapping("/vo/{id}")
    public ResponseEntity<?> getPostWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getPostWithUserById(id), "get post with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/vo")
    public ResponseEntity<?> getPostWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(postService.getPostWithUser(), "get post with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no post", "400"), HttpStatus.BAD_REQUEST);
        }
    }

//    Likes Template
    @GetMapping("/likes/vo/{id}")
    public ResponseEntity<?> getLikesWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesWithUserById(id), "get likes with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/likes/vo")
    public ResponseEntity<?> getLikesWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(likesService.getLikesWithUser(), "get likes with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes", "400"), HttpStatus.BAD_REQUEST);
        }
    }

//    Comment Template
    @GetMapping("/comment/vo/{id}")
    public ResponseEntity<?> getCommentWithUserById(@PathVariable String  id){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentWithUserById(id), "get likes with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment/vo")
    public ResponseEntity<?> getCommentWithUser(){
        try {
            return ResponseEntity.ok(commonResponseGenerator.response(commentService.getCommentWithUser(), "get likes with user success", "200"));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no likes", "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/c")
    public ResponseEntity<?> get(@RequestBody TokenResponse token){
        try{
            return ResponseEntity.ok(postService.contoh(token));
        }catch (Exception e){
            return new ResponseEntity<>(commonResponseGenerator.response(null, "there is no user with id ", "400"),HttpStatus.BAD_REQUEST);
        }
    }

}
