package com.nhnacademy.front.shop.payment.dto;

public record PaymentCancelRequest(
        Long orderId,
        String paymentKey,
        Integer amount,
        PaymentType paymentType,
        String cancelReason
){
}