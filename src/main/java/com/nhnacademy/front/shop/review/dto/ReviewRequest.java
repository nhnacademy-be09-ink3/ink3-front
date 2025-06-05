package com.nhnacademy.front.shop.review.dto;

public record ReviewRequest(
    Long userId,
    Long orderBookId,
    String title,
    String content,
    int rating
) {
}
