package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.kafka.KafkaConsumer;
import com.dwibagus.postcollab.kafka.KafkaProducer;
import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.repository.CategoryRepository;
import com.dwibagus.postcollab.repository.FilePostRepository;
import com.dwibagus.postcollab.repository.ImageRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.security.JwtTokenProvider;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
//    user ctrl + shif + t to create unit testing
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final FilePostRepository filePostRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

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
    public Post editById(String id, Post post){
        Post post1 = postRepository.findById(id).get();
        if (post.getName() != null){
            post1.setName(post.getName());
        }
        if (post.getCategoryId() != null){
            post1.setCategoryId(post.getCategoryId());
        }
        if (post.getUserId() != null){
            post1.setUserId(post.getUserId());
        }
        post1.setUpdated_at(new Date());
        postRepository.save(post1);
        return post1;
    }

    public Post deleteById(String id) {
        Post post = postRepository.findById(id).get();

//      send kafka
        String data = "post id = " + id;
        producer.produce(id);

        postRepository.deleteById(id);
        return post;
    }


    @Override
    public FilePost uploadFile(MultipartFile file) throws IOException {
        FilePost filePost = new FilePost();
        if (file != null){
            file.transferTo(new File("D:\\cth\\" + file.getOriginalFilename()));
            filePost.setName(file.getOriginalFilename());
        }
        return filePostRepository.save(filePost);
    }

    @Override
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }


    @Override
    public List<ResponseTemplateVO> getPostWithUser(){
        List<ResponseTemplateVO> allPostWithUser = new ArrayList<>();
        ResponseTemplateVO vo = new ResponseTemplateVO();
        List<Post> posts = postRepository.findAll();
        for (int i = 0; i < posts.size(); i++){
            vo = this.getPostWithUserById(posts.get(i).getId());
            allPostWithUser.add(vo);
        }
        return allPostWithUser;

    }


    @Override
    public ResponseTemplateVO getPostWithUserById(String id){
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not Found");
        });
        System.out.println(post.getCategoryId());
        Category category = categoryRepository.findById(post.getCategoryId()).orElseThrow(() -> {
            throw new RuntimeException("Not Found");
        });
        System.out.println(category.getCategoryName());
        System.out.println(post.getUserId());
        User user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + post.getUserId(), User.class);
        System.out.println(user.getEmail());
        vo.setUser(user);
        vo.setPost(post);
        vo.setCategory(category);

        return vo;
    }

    @Override
    public String contoh(TokenResponse token){
        if (jwtTokenProvider.validateToken(token.getToken())){
            return jwtTokenProvider.getUsername(token.getToken());
        }
        return "tidak";
    }

}
