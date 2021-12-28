package com.dwibagus.postcollab.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
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
    private String image = null;
    private Date created_at = new Date();
    private Date updated_at = new Date();

}
