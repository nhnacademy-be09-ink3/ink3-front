package com.nhnacademy.front.shop.review.dto;

public record ReviewFormUpdateRequest(
    String title,
    String content,
    int rating
) {
    public ReviewUpdateRequest toRequest() {
        return new ReviewUpdateRequest(title, content, rating);
    }
}
