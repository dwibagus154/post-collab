package com.dwibagus.postcollab.vo.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private Integer isAdmin;
    private boolean active;
    private Date created_at;
    private Date updated_at;

}
