package com.nhnacademy.front.shop.coupon.store.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StoresResponse(
        Long storeId,
        Long userId,
        String userName,
        Long couponId,
        String couponName,
        OriginType originType,
        Long originId,
        CouponStatus status,

        // 날짜/시간 필드
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")  // 서버 형식에 맞춤
        LocalDateTime issuedAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
        LocalDateTime expiresAt,

        // 정책 값
        int discountValue,
        int discountPercentage
) {}
