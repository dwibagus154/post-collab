package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {

    Post create(Post post);
    Post findById(String id);
    Image uploadFile(MultipartFile file) throws IOException;

}
