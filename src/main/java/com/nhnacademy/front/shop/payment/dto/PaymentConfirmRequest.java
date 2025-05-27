package com.nhnacademy.front.shop.payment.dto;

public record PaymentConfirmRequest(
        Long userId,
        Long orderId,
        String paymentKey,
        String orderUUID,
        Integer usedPointAmount,
        Integer discountAmount,
        Integer amount,
        PaymentType paymentType
){

}
