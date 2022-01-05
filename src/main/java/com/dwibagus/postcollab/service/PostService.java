package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.payload.TokenResponse;
import com.dwibagus.postcollab.vo.post.ResponsePostWithComment;
import com.dwibagus.postcollab.vo.post.ResponsePostWithLikes;
import com.dwibagus.postcollab.vo.post.ResponseTemplateVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PostService {

    ResponseTemplateVO create(Post post);
    Post findById(String id);

    List<Post> getAllPost();

    ResponseTemplateVO editById(String id, Post post);

    Post deleteById(String id);

    List<ResponseTemplateVO> getPostWithUser();
    ResponseTemplateVO getPostWithUserById(String id);

    ResponsePostWithComment findCommentById(String id);

    ResponsePostWithLikes findLikesById(String id);

    FilePost uploadFile(MultipartFile file, String id) throws IOException;

    List<FilePost> getFilePost();
}
