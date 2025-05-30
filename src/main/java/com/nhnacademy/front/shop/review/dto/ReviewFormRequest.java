package com.nhnacademy.front.shop.review.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewFormRequest(
    String title,
    String content,
    int rating,
    List<MultipartFile> images
) {
    @JsonCreator
    public ReviewFormRequest(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("rating") int rating,
        @JsonProperty("images") List<MultipartFile> images
    ) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.images = images;
    }

    public ReviewRequest toRequest(Long userId, Long orderBookId) {
        return new ReviewRequest(userId, orderBookId, title, content, rating);
    }
}
