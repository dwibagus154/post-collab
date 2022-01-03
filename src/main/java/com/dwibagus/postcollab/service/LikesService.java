package com.dwibagus.postcollab.service;

import com.dwibagus.postcollab.model.Likes;
import com.dwibagus.postcollab.vo.likes.ResponseLikesTemplate;

import java.util.List;

public interface LikesService {

    List<Likes> getAllLikes();

    Likes getLikesById(String id);

    ResponseLikesTemplate createLikes(Likes likes);

    Likes deleteLikesById(String id);

    ResponseLikesTemplate getLikesWithUserById(String id);

    List<ResponseLikesTemplate> getLikesWithUser();
}
