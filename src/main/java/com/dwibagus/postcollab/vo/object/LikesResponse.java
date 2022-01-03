package com.dwibagus.postcollab.vo.object;

import com.dwibagus.postcollab.vo.object.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesResponse {
    private String id;
    private User user;
    private Date created_at;
    private Date updated_at;
}
