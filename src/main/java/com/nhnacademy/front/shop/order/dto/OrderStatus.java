package com.nhnacademy.front.shop.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    CONFIRMED("대기"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    REFUNDED("반품완료"),
    CANCELLED("주문취소"),
    FAILED("결제실패");

    private final String label;


    @JsonCreator
    public static OrderStatus getStatus(String str) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.label.equals(str)) {
                return status;
            }
        }
        return CONFIRMED;
    }
}
