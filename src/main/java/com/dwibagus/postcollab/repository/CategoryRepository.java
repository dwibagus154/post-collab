package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
