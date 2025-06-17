package com.nhnacademy.front.shop.like.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.like.client.LikeClient;
import com.nhnacademy.front.shop.like.client.dto.LikeCreateRequest;
import com.nhnacademy.front.shop.like.client.dto.LikeExistResponse;
import com.nhnacademy.front.shop.like.client.dto.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeClient likeClient;

    public PageResponse<LikeResponse> getCurrentUserLikes(int page, int size, String sort) {
        return likeClient.getCurrentUserLikes(page, size, sort).data();
    }

    public LikeExistResponse checkLiked(long bookId) {
        return likeClient.checkLiked(bookId).data();
    }

    public LikeResponse createCurrentUserLike(LikeCreateRequest request) {
        return likeClient.createCurrentUserLike(request).data();
    }

    public void deleteCurrentUserLike(long likeId) {
        likeClient.deleteCurrentUserLike(likeId);
    }
}
