package com.nhnacademy.front.shop.payment.client;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shop-service", url = "${feign.url.shop}")
public interface PaymentClient {
    // payment 결제 승인 요청
    @PostMapping("/payments/confirm")
    CommonResponse<PaymentResponse> confirmPayment(@RequestBody PaymentConfirmRequest request);

    @GetMapping("/payments/{orderId}")
    CommonResponse<PaymentResponse> getPayment(@PathVariable("orderId") long orderId);

}
