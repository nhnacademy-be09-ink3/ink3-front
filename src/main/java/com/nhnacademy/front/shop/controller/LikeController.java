package com.nhnacademy.front.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.shop.like.client.dto.LikeCreateRequest;
import com.nhnacademy.front.shop.like.service.LikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/likes")
    @ResponseBody
    public Long createLike(@RequestParam Long bookId) {
        return likeService.createCurrentUserLike(new LikeCreateRequest(bookId)).id();
    }

    @PostMapping("/likes/delete")
    @ResponseBody
    public void deleteLike(@RequestParam Long likeId) {
        likeService.deleteCurrentUserLike(likeId);
    }
}
