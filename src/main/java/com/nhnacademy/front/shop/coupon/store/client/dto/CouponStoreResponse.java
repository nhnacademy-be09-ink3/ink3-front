package com.nhnacademy.front.shop.coupon.store.client.dto;

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
        LocalDateTime issuedAt
) {
}

