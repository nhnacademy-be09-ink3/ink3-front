package com.nhnacademy.front.shop.coupon.store.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.coupon.store.client.dto.CouponIssueRequest;
import com.nhnacademy.front.shop.coupon.store.client.dto.StoresResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "couponStore", url = "${feign.url.shop}")
public interface CouponStore {

    @PostMapping("/users/coupon-stores")
    CommonResponse<StoresResponse> issueCoupon(@RequestBody CouponIssueRequest request);
}