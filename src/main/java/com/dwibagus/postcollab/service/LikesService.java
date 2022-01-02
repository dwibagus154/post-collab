package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.vo.ResponseLikesTemplate;

import java.util.List;

public interface LikesService {
    Likes createLikes(Likes likes);

    List<Likes> getAllLikes();

    Likes getLikesById(String id);

    Likes deleteLikesById(String id);

    ResponseLikesTemplate getLikesWithUserById(String id);

    List<ResponseLikesTemplate> getLikesWithUser();
}
