package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
