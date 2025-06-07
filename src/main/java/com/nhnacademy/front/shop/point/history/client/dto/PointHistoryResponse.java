package com.nhnacademy.front.shop.point.history.client.dto;

import java.time.LocalDateTime;

public record PointHistoryResponse(
        Long id,
        Integer delta,
        PointHistoryStatus status,
        String description,
        LocalDateTime createdAt
) {
}
