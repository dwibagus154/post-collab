package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.model.Image;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.vo.ResponseTemplateVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post create(Post post);
    Post findById(String id);
    FilePost uploadFile(MultipartFile file) throws IOException;

    List<Post> getAllPost();

    Post editById(String id, Post post);

    Post deleteById(String id);

    List<ResponseTemplateVO> getPostWithUser();
    ResponseTemplateVO getPostWithUserById(String id);

    String contoh(TokenResponse token);
}
