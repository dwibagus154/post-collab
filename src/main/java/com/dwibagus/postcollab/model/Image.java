package com.dwibagus.postcollab.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("image")
public class Image {
    @Id
    private String id;
    private String name = null;
}
