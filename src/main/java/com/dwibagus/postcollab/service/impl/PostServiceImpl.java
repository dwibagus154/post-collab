package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.ImageRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.PostService;
import com.dwibagus.postcollab.vo.ResponseTemplateVO;
import com.dwibagus.postcollab.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public ResponseTemplateVO getPostWithUser(String id){
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not Found");
        });
        System.out.println(post.getUserId());
        User user = restTemplate.getForObject("http://localhost:8080/auth/user/" + post.getUserId(), User.class);
        System.out.println(user.getEmail());
        vo.setUser(user);
        vo.setPost(post);

        return vo;
    }
}
