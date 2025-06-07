package com.nhnacademy.front.shop.guest.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.guest.client.GuestPaymentClient;
import com.nhnacademy.front.shop.guest.dto.GuestPaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GuestPaymentService {
    private final GuestPaymentClient guestPaymentClient;

    public PaymentResponse confirmPayment(GuestPaymentConfirmRequest request){
        CommonResponse<PaymentResponse> paymentResponse = guestPaymentClient.confirmPayment(request);
        return paymentResponse.data();
    }

    public void dealWithPaymentFail(long orderId) {
        guestPaymentClient.dealWithPaymentFail(orderId);
    }
}
