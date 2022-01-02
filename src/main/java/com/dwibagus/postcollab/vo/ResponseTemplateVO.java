package com.dwibagus.postcollab.vo;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {
    private Post post;
    private User user;
    private Category category;
}
