package com.nhnacademy.front.shop.couponStore.client.dto;

import java.time.LocalDateTime;

public record CouponStoreDto(
        Long storeId,
        Long couponId,
        String couponName,
        LocalDateTime expiresAt,
        OriginType originType,
        Long originId,
        CouponStatus status,
        DiscountType discountType,
        Integer discountValue,
        Integer discountPercentage,
        Integer maximumDiscountAmount
) {}
