package com.nhnacademy.front.shop.order.예시DTO;

import java.time.LocalDateTime;

public record PointHistoryResponse(
        Long id,
        Integer delta,
        PointHistoryStatus status,
        String description,
        LocalDateTime createdAt
) {
}
