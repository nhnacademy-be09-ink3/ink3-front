package com.nhnacademy.front.shop.coupon.coupon.client.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CouponResponse(
        Long couponId,
        String name,
        Long policyId,
        String policyName,
        int discountRate,
        int discountValue,
        LocalDateTime issuableFrom,
        LocalDateTime expiresAt,
        boolean isActive,
        LocalDateTime createdAt,
        List<BookInfo> books,
        List<CategoryInfo> categories
) {
    public record BookInfo(Long originId, Long id, String title, String originType) {}
    public record CategoryInfo(Long originId, Long id, String name, String originType) {}
}
