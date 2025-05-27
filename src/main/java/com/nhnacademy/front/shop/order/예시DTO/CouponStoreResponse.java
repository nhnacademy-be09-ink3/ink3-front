package com.nhnacademy.front.shop.order.예시DTO;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CouponStoreResponse(
        Long storeId,
        Long userId,
        String userName,
        Long couponId,
        String couponName,
        CouponStatus status,
        LocalDateTime issuedAt
) {
}

