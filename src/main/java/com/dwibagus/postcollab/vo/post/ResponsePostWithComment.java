package com.dwibagus.postcollab.vo.post;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.vo.object.CommentResponse;
import com.dwibagus.postcollab.vo.object.User;

import java.util.Date;
import java.util.List;

public class ResponsePostWithComment {
    private String id;
    private String name;
    private User user;
    private Category category;
    private String file;
    private Integer totalLikes;
    private Integer totalComment;
    private Date created_at;
    private Date updated_at;
    private List<CommentResponse> comment;
}
