package com.dwibagus.postcollab.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("file")
public class FilePost {
    @Id
    private String id;
    private String name = null;
    private Date created_at = new Date();
    private Date updated_at = new Date();
}