package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService  {

    Category createCategory(Category category);
    Category findByIdCategory(String id);

    List<Category> getAllCategory();

    Category editCategory(String id, Category category);

    Category deleteCategory(String id);
}
