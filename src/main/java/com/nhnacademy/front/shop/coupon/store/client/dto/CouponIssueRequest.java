package com.nhnacademy.front.shop.coupon.store.client.dto;

import jakarta.validation.constraints.NotNull;

public record CouponIssueRequest(
        @NotNull(message = "userId는 필수입니다.") Long userId,
        @NotNull(message = "couponId는 필수입니다.") Long couponId,
        @NotNull OriginType originType,
        Long originId
) {}
