package com.nhnacademy.front.shop.point.policy.client.dto;

public record PointPolicyCreateRequest(
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer defaultRate
) {
}
