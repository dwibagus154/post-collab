package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.kafka.KafkaConsumer;
import com.dwibagus.postcollab.kafka.KafkaProducer;
import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.repository.*;
import com.dwibagus.postcollab.security.JwtTokenProvider;
import com.dwibagus.postcollab.service.LikesService;
import com.dwibagus.postcollab.service.PostService;
import com.dwibagus.postcollab.vo.post.ResponsePostWithComment;
import com.dwibagus.postcollab.vo.post.ResponsePostWithLikes;
import com.dwibagus.postcollab.vo.post.ResponseTemplateVO;
import com.dwibagus.postcollab.vo.object.User;
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

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
//    user ctrl + shif + t to create unit testing
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final FilePostRepository filePostRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

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

//        create response
        vo.setId(post.getId());
        vo.setName(post.getName());
        vo.setUser(user);
        vo.setCategory(category);
        vo.setFile(post.getFile());
        vo.setTotalComment(post.getTotalComment());
        vo.setTotalLikes(post.getTotalLikes());
        vo.setCreated_at(post.getCreated_at());
        vo.setUpdated_at(post.getUpdated_at());


        return vo;
    }

    public ResponsePostWithComment findCommentById(String id){
//        ResponsePostWithComment responsePostWithComment = new ResponsePostWithComment();
//        Post post = postRepository.findById(id).get();
//        List<Comment> commentList =
        return  null;

    }

    public ResponsePostWithLikes findLikesById(String id){
        return null;
    }

}
