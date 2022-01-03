package com.dwibagus.postcollab.vo.likes;

import com.dwibagus.postcollab.model.Post;
import com.dwibagus.postcollab.vo.object.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLikesTemplate {
    private Post post;
    private User user;
    private Date created_at;
    private Date updated_at;
}
