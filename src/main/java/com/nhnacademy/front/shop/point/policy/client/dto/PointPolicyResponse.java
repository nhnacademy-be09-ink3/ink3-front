package com.nhnacademy.front.shop.point.policy.client.dto;

import java.time.LocalDateTime;

public record PointPolicyResponse(
        Long id,
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer defaultRate,
        Boolean isActive,
        LocalDateTime createdAt
) {
}
