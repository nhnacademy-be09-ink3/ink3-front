package com.nhnacademy.front.shop.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    CREATED("결제대기"),
    CONFIRMED("대기"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    REFUNDED("반품완료"),
    CANCELLED("주문취소"),
    FAILED("결제실패");

    private final String label;


    @JsonCreator
    public static OrderStatus from(String value) {
        try {
            return OrderStatus.valueOf(value); // name 기준 매핑
        } catch (IllegalArgumentException e) {
            return CREATED; // 잘못된 값이 들어왔을 경우에만 fallback
        }
    }
}
