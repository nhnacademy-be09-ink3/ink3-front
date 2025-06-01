package com.nhnacademy.front.shop.payment.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.payment.client.PaymentClient;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
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
}
