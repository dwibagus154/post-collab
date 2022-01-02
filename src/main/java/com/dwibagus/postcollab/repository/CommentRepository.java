package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
