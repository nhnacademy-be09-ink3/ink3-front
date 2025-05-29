package com.nhnacademy.front.shop.payment.dto;

public record PaymentConfirmRequest(
        Long userId,
        Long orderId,
        String paymentKey,
        String orderUUID,
        Integer discountAmount,
        Integer usedPointAmount,
        Integer amount,
        PaymentType paymentType
){

}
