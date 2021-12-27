package com.dwibagus.postcollab.controller;


import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.service.CategoryService;
import com.dwibagus.postcollab.service.PostService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Post post){
        Post post1 = postService.create(post);
        return ResponseEntity.ok(post1);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println("masuk sini");
        Image image = postService.uploadFile(file);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        try {
            Post post = postService.findById(id);
            return ResponseEntity.ok(post);
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }


    }


//    Kategori

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(Category category){
//        System.out.println(category.getName());
        Category category1 = categoryService.createCategory(category);
        return ResponseEntity.ok(category1);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<Category> findByIdCategory(@PathVariable String id){
        try {
            Category category = categoryService.findByIdCategory(id);
            return ResponseEntity.ok(category);
        }catch (Exception e){
            if (e.getMessage().equals("Not found")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }
}
