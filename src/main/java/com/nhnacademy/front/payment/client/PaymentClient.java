package com.nhnacademy.front.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "shop-service")
public class PaymentClient {
//    @GetMapping("/orders")
//    Commonn
}
