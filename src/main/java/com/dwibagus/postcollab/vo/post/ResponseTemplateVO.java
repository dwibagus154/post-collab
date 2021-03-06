package com.dwibagus.postcollab.vo.post;

import com.dwibagus.postcollab.model.Category;
import com.dwibagus.postcollab.model.FilePost;
import com.dwibagus.postcollab.vo.object.FileResponse;
import com.dwibagus.postcollab.vo.object.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {
    private String id;
    private String name;
    private String description;
    private User user;
    private Category category;
    private FileResponse file;
    private Integer totalLikes;
    private Integer totalComment;

    private Date created_at;

    private Date updated_at;

}
