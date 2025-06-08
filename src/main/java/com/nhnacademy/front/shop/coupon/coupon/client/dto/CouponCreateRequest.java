package com.nhnacademy.front.shop.coupon.coupon.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record CouponCreateRequest(
        @NotNull Long policyId,
        @NotBlank String name,
        @NotNull LocalDateTime issuableFrom,
        @NotNull LocalDateTime expiresAt,
        List<Long> bookIdList,
        List<Long> categoryIdList
) {
    public CouponCreateRequest {
        // null 이면 빈 리스트로 치환
        if (bookIdList == null) {
            bookIdList = List.of();
        }
        if (categoryIdList == null) {
            categoryIdList = List.of();
        }
    }
}
