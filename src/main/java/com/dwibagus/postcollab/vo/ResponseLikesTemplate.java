package com.dwibagus.postcollab.vo;

import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLikesTemplate {
    private Post post;
    private User user;
    private Likes likes;
}
