package com.nhnacademy.front.shop.guest.dto;

import com.nhnacademy.front.shop.payment.dto.PaymentType;
import lombok.Builder;

@Builder
public record GuestPaymentConfirmRequest(
        Long orderId,
        String paymentKey,
        String orderUUID,
        Integer amount,
        PaymentType paymentType
){
}
