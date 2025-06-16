package com.nhnacademy.front.shop.like.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.like.client.dto.LikeCreateRequest;
import com.nhnacademy.front.shop.like.client.dto.LikeExistResponse;
import com.nhnacademy.front.shop.like.client.dto.LikeResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "likeClient", url = "${feign.url.shop}/users/me/likes")
public interface LikeClient {
    @GetMapping
    CommonResponse<PageResponse<LikeResponse>> getCurrentUserLikes(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort
    );

    @GetMapping("/books/{bookId}")
    CommonResponse<LikeExistResponse> checkLiked(@PathVariable long bookId);

    @PostMapping
    CommonResponse<LikeResponse> createCurrentUserLike(@RequestBody @Valid LikeCreateRequest request);

    @DeleteMapping("/{likeId}")
    ResponseEntity<Void> deleteCurrentUserLike(@PathVariable long likeId);
}
