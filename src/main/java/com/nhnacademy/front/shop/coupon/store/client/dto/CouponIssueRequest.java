package com.nhnacademy.front.shop.coupon.store.client.dto;

import jakarta.validation.constraints.NotNull;

public record CouponIssueRequest(
        @NotNull(message = "couponId는 필수입니다.") Long couponId,
        @NotNull String originType,
        Long originId
) {}
