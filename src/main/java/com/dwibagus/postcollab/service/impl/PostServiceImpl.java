package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.ImageRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Override
    public Post create(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post findById(String id){
        return postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not Found");
        });
    }

    @Override
    public Image uploadFile(MultipartFile file) throws IOException {
        Image image = new Image();
        if (file != null){
            file.transferTo(new File("D:\\cth\\" + file.getOriginalFilename()));
            image.setName(file.getOriginalFilename());
        }
        return imageRepository.save(image);
    }
}
