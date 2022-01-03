package com.dwibagus.postcollab.vo.post;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.vo.object.CommentResponse;
import com.dwibagus.postcollab.vo.object.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostWithComment {
    private String id;
    private String name;
    private User user;
    private Category category;
    private FilePost file;
    private Integer totalLikes;
    private Integer totalComment;
    private Date created_at;
    private Date updated_at;
    private List<CommentResponse> comment;
}
