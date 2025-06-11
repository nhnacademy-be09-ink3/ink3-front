package com.nhnacademy.front.shop.coupon.store.client.dto;

public enum CouponStatus {
    READY,     // 발급만 된 상태
    USED,      // 실제 사용됨
    EXPIRED    // 유효기간 지남
}
