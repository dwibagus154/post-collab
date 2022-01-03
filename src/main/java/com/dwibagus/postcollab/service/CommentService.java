package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.vo.comment.ResponseCommentTemplate;

import java.util.List;

public interface CommentService {

    List<Comment> getComment();

    Comment getCommentById(String id);

    ResponseCommentTemplate createComment(Comment comment);

    ResponseCommentTemplate updateCommentById(String id, Comment comment);

    Comment deleteCommentById(String id);

    ResponseCommentTemplate getCommentWithUserById(String id);

    List<ResponseCommentTemplate> getCommentWithUser();
}
