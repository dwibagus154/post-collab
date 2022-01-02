package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.vo.ResponseCommentTemplate;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);

    List<Comment> getComment();

    Comment getCommentById(String id);

    Comment updateCommentById(String id, Comment comment);

    Comment deleteCommentById(String id);

    ResponseCommentTemplate getCommentWithUserById(String id);

    List<ResponseCommentTemplate> getCommentWithUser();
}
