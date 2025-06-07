package com.nhnacademy.front.shop.coupon.store.client.dto;

import java.time.LocalDateTime;

public record CouponStoreUpdateRequest(
        /** used 상태로만 업데이트할 경우 */
        CouponStatus couponStatus,
        /** isUsed=true 일 때 기록할 사용 시각 */
        LocalDateTime usedAt
) {}
