package com.nhnacademy.front.shop.coupon.coupon.client.dto;

import java.time.LocalDateTime;

public record CouponView(
        Long couponId,
        String name,
        int discountRate,
        int discountValue,
        LocalDateTime expiresAt,
        String originType,
        Long originId
) {}