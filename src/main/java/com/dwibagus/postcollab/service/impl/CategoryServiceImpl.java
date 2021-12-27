package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.repository.CategoryRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

}
