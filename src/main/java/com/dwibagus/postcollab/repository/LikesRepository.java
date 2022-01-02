package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikesRepository extends MongoRepository<Likes, String> {
}
