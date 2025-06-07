package com.nhnacademy.front.shop.review.dto;

public record MeReviewRequest(
    Long orderBookId,
    String title,
    String content,
    int rating
) {
}
