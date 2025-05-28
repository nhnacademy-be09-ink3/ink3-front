package com.nhnacademy.front.shop.review.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ReviewFormRequest(
    String title,
    String content,
    int rating,
    List<MultipartFile> images
) {
    public ReviewRequest toRequest(Long userId, Long orderBookId) {
        return new ReviewRequest(userId, orderBookId, title, content, rating);
    }
}
