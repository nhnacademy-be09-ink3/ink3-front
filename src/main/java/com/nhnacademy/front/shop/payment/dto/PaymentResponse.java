package com.nhnacademy.front.shop.payment.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
        long id,
        long orderId,
        String paymentKey,
        int usedPointAmount,
        int discountAmount,
        int paymentAmount,
        PaymentType paymentType,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
){
}
