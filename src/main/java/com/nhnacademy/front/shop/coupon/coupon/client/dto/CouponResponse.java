package com.nhnacademy.front.shop.coupon.coupon.client.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CouponResponse(
        Long couponId,
        String name,
        Long policyId,
        String policyName,
        LocalDateTime issuableFrom,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        List<BookInfo> books,
        List<CategoryInfo> categories
) {
    public record BookInfo(Long originId, Long id, String title) {}
    public record CategoryInfo(Long originId, Long id, String name) {}
}
