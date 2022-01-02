package com.dwibagus.postcollab.vo;

import com.dwibagus.postcollab.model.Comment;
import com.dwibagus.postcollab.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentTemplate {
    private Post post;
    private User user;
    private Comment comment;
}
