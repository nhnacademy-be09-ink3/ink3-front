package com.nhnacademy.front.shop.review.dto;

public record ReviewUpdateRequest(
    String title,
    String content,
    int rating
) {
}
