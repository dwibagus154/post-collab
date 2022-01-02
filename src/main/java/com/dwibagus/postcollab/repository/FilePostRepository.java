package com.dwibagus.postcollab.repository;

import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilePostRepository extends MongoRepository<FilePost, String> {
}
