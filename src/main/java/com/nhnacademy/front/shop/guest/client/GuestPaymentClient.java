package com.nhnacademy.front.shop.guest.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.guest.dto.GuestPaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "guestPaymentClient", url = "${feign.url.shop}")
public interface GuestPaymentClient {

    @PostMapping("/guest-payment/confirm")
    CommonResponse<PaymentResponse> confirmPayment(GuestPaymentConfirmRequest request);

    @PostMapping("/guest-payment/{orderId}/fail")
    CommonResponse<Void> dealWithPaymentFail(@PathVariable("orderId") long orderId);
}
