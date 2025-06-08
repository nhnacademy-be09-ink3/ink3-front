package com.nhnacademy.front.shop.payment.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.payment.client.PaymentClient;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.ZeroPaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentClient paymentClient;

    public PaymentResponse getPayment(long orderId){
        CommonResponse<PaymentResponse> paymentResponse = paymentClient.getPayment(orderId);
        return paymentResponse.data();
    }

    public PaymentResponse confirmPayment(PaymentConfirmRequest confirmRequest){
        CommonResponse<PaymentResponse> paymentResponse = paymentClient.confirmPayment(confirmRequest);
        return paymentResponse.data();
    }

    // 결제 생성 (0원 결제 시)
    public PaymentResponse createZeroPayment(ZeroPaymentRequest zeroPaymentRequest){
        CommonResponse<PaymentResponse> paymentResponse = paymentClient.createZeroPayment(zeroPaymentRequest);
        return paymentResponse.data();
    }

    public void dealWithPaymentFail(long orderId) {
        paymentClient.dealWithPaymentFail(orderId);
    }

    public void dealWithPaymentCancel(long orderId) {
        paymentClient.dealWithPaymentCancel(orderId);
    }
}
