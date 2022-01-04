package com.dwibagus.postcollab.model;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("postcollab")
public class Post {

    @Id
    private String id;
    private String categoryId;
    private String name;
    private Long userId;
    private String file = null;
    private Integer totalLikes = 0;
    private Integer totalComment = 0;

    private Date created_at = new Date();

    private Date updated_at = new Date();

}
