package com.dwibagus.postcollab.model;


import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("comment")
public class Comment {
    @Id
    private String id;
    private Long userId;
    private String postId;
    private String description;
    private Date created_at = new Date();
    private Date updated_at = new Date();

}
