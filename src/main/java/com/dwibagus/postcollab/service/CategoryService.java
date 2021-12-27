package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService  {

    Category createCategory(Category category);
    Category findByIdCategory(String id);
}
