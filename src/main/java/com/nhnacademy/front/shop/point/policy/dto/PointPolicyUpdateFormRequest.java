package com.nhnacademy.front.shop.point.policy.dto;

public record PointPolicyUpdateFormRequest(
        Long id,
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer imageReviewPoint,
        Integer defaultRate
) {
}
