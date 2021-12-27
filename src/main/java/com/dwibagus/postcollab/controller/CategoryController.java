//package com.dwibagus.postcollab.controller;
//
//
//import com.dwibagus.postcollab.model.Category;
//import com.dwibagus.postcollab.service.CategoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/category")
//@RequiredArgsConstructor
//public class CategoryController {
//
//    private final CategoryService categoryService;
//
//    @PostMapping
//    public ResponseEntity<?> create(Category category){
//        System.out.println(category.getCategoryId()); x
//        System.out.println(category.getName());
//        Category category1 = categoryService.create(category);
//        return ResponseEntity.ok(category1);
//    }
//
//
//    @GetMapping("{id}")
//    public ResponseEntity<Category> findById(@PathVariable String id){
//        try {
//            Category category = categoryService.findById(id);
//            return ResponseEntity.ok(category);
//        }catch (Exception e){
//            if (e.getMessage().equals("Not found")){
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//}
