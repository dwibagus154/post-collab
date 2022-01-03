package com.dwibagus.postcollab.vo.object;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String id;
    private User user;
    private String description;
    private Date created_at;
    private Date updated_at;
}
