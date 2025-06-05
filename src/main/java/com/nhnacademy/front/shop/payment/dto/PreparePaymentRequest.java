package com.nhnacademy.front.shop.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PreparePaymentRequest {
    private long userId;
    private long orderId;
    private String orderUUID;
    private int usedPointAmount;
    private int discountAmount;
    private int amount;
}
