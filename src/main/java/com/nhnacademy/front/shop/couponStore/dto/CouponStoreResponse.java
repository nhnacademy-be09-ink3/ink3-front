package com.nhnacademy.front.shop.couponStore.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CouponStoreResponse(
        Long storeId,
        Long userId,
        String userName,
        Long couponId,
        String couponName,
        OriginType originType,
        Long originId,
        CouponStatus status,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt,
        Integer discountValue,
        Integer discountPercentage
) {
}

