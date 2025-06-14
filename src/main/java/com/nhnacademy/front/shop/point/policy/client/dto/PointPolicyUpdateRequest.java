package com.nhnacademy.front.shop.point.policy.client.dto;

public record PointPolicyUpdateRequest(
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer imageReviewPoint,
        Integer defaultRate
) {
}
