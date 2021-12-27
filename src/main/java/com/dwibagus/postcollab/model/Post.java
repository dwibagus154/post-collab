package com.dwibagus.postcollab.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("postcollab")
public class Post {

    @Id
    private String id;
    private String categoryId;
    private String name;
    private Long userId;
    private String image = null;

}
