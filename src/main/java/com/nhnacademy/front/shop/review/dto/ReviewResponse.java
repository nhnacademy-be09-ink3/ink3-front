package com.nhnacademy.front.shop.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
    Long id,
    Long userId,
    Long bookId,
    Long orderBookId,
    String title,
    String content,
    int rating,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    List<String> images,
    String description
) {
}
