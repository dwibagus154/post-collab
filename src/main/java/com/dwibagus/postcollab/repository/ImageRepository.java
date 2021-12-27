package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}