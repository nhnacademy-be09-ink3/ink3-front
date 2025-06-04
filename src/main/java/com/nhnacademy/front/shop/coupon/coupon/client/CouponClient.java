package com.nhnacademy.front.shop.coupon.coupon.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "couponClient", url = "${feign.url.shop}/")
public interface CouponClient {
}
