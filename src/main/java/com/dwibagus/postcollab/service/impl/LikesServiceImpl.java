package com.dwibagus.postcollab.service.impl;

import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.repository.LikesRepository;
import com.dwibagus.postcollab.repository.PostRepository;
import com.dwibagus.postcollab.service.LikesService;
import com.dwibagus.postcollab.vo.ResponseLikesTemplate;
import com.dwibagus.postcollab.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Likes createLikes(Likes likes){
        Post post = postRepository.findById(likes.getPostId()).get();
        User user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + likes.getUserId(), User.class);
        if (post != null && user != null){
            post.setTotalLikes(post.getTotalLikes()+1);
            post.setUpdated_at(new Date());
            postRepository.save(post);
        }
        return likesRepository.save(likes);
    }

    @Override
    public List<Likes> getAllLikes(){
        return likesRepository.findAll();
    }

    @Override
    public Likes getLikesById(String id){
        return likesRepository.findById(id).orElseThrow(() -> {
            throw  new RuntimeException("Not Found");
        });
    }


    @Override
    public Likes deleteLikesById(String id){
        Likes likes = likesRepository.findById(id).get();
        Post post = postRepository.findById(likes.getPostId()).get();
        if (post != null){
            post.setTotalComment(post.getTotalComment()-1);
            post.setUpdated_at(new Date());
            postRepository.save(post);
        }
        likesRepository.deleteById(id);
        return likes;
    }

    @Override
    public ResponseLikesTemplate getLikesWithUserById(String id){
        ResponseLikesTemplate responseLikesTemplate = new ResponseLikesTemplate();
        Likes likes = likesRepository.findById(id).get();

        User user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + likes.getUserId(), User.class);

        responseLikesTemplate.setLikes(likes);
        responseLikesTemplate.setPost(postRepository.findById(likes.getPostId()).get());
        responseLikesTemplate.setUser(user);
        return responseLikesTemplate;
    }

    @Override
    public List<ResponseLikesTemplate> getLikesWithUser(){
        List<ResponseLikesTemplate> allLikesTemplate = new ArrayList<>();
        ResponseLikesTemplate responseLikesTemplate = new ResponseLikesTemplate();

        User user = new User();
        List<Likes> allLikes = likesRepository.findAll();
        for (int i = 0; i < allLikes.size(); i++){
            user = restTemplate.getForObject("http://localhost:8080/auth/vo/user/" + allLikes.get(i).getUserId(), User.class);
            responseLikesTemplate.setLikes(allLikes.get(i));
            responseLikesTemplate.setPost(postRepository.findById(allLikes.get(i).getPostId()).get());
            responseLikesTemplate.setUser(user);

            allLikesTemplate.add(responseLikesTemplate);
        }
        return allLikesTemplate;

    }
}