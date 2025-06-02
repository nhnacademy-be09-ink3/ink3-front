package com.nhnacademy.front.shop.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewListResponse(
    Long id,
    Long userId,
    Long orderBookId,
    String userName,
    String title,
    String content,
    int rating,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    List<ReviewImageResponse> images
) {
}
