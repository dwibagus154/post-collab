package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.repository.CategoryRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public Category findByIdCategory(String id){
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw  new RuntimeException("Not Found");
        });
    }

    @Override
    public Category editCategory(String id, Category category){
        Category category1 = categoryRepository.findById(id).get();
        category1.setCategoryName(category.getCategoryName());
        category1.setUpdated_at(new Date());
        categoryRepository.save(category1);
        return category1;
    }

    @Override
    public Category deleteCategory(String id){
        Category category = categoryRepository.findById(id).get();
        categoryRepository.deleteById(id);
        return category;
    }

    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }


}
