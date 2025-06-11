package com.nhnacademy.front.shop.coupon.store.client.dto;

import java.time.LocalDateTime;
public record CouponStoreUpdateResponse(
        Long storeId,
        CouponStatus status,
        LocalDateTime usedAt,
        String message
) {
}
