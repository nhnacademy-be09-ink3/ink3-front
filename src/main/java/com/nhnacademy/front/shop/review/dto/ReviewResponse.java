package com.nhnacademy.front.shop.review.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    Long userId,
    Long orderBookId,
    String title,
    String content,
    int rating,
    LocalDateTime createdAt
) {
}
