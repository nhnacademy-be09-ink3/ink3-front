package com.nhnacademy.front.shop.payment.dto;

import lombok.Builder;

@Builder
public record ZeroPaymentRequest (
    Long userId,
    Long orderId,
    Integer discountAmount,
    Integer usedPointAmount,
    Integer amount
){
}
