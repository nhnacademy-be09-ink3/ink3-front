package com.nhnacademy.front.shop.couponStore.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.couponStore.dto.CouponStoreDto;
import com.nhnacademy.front.shop.couponStore.dto.CouponStoreResponse;

@FeignClient(name = "couponStoreClient", url = "${feign.url.shop}")
public interface CouponStoreClient {
    // 유저 쿠폰 전체 조회
    @GetMapping("/me/users/coupon-stores")
    CommonResponse<PageResponse<CouponStoreResponse>> getStoresByUserId(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String sort
    );

    // 유저의 미사용 쿠폰 조회
    @GetMapping("/me/users/coupon-stores/status-unused")
    CommonResponse<PageResponse<CouponStoreResponse>> getUnusedStores(@RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String sort);

    // 유저의 사용 & 만료 쿠폰 조회
    @GetMapping("/me/users/coupon-stores/status-used")
    CommonResponse<PageResponse<CouponStoreResponse>> getUsedExpiredStores(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String sort);

    @GetMapping("/applicable-coupons")
    CommonResponse<List<CouponStoreDto>> getApplicableCoupons(
            @RequestParam long userId,
            @RequestParam long bookId
    );

    @GetMapping("coupon-stores/exists")
    Boolean existsCouponStore(
            @RequestParam long userId,
            @RequestParam long originId
    );
}
