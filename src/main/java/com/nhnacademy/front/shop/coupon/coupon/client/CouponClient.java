package com.nhnacademy.front.shop.coupon.coupon.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponCreateRequest;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "couponClient", url = "${feign.url.shop}")
public interface CouponClient {

    /**
     * 쿠폰 생성
     */
    @PostMapping("/coupons")
    CommonResponse<CouponResponse> createCoupon(@RequestBody CouponCreateRequest request);

    /**
     * ID로 단건 조회
     */
    @GetMapping("/coupons/{couponId}")
    CommonResponse<CouponResponse> getById(@PathVariable("couponId") Long couponId);

    /**
     * 전체 조회 (페이징)
     * @param page  페이지 인덱스 (0부터 시작)
     * @param size  페이지 크기
     */
    @GetMapping("/coupons")
    CommonResponse<PageResponse<CouponResponse>> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    /**
     * 쿠폰 수정
     */
    @PutMapping("/coupons/{couponId}")
    CommonResponse<CouponResponse> updateCoupon(
            @PathVariable("couponId") Long couponId,
            @RequestBody CouponUpdateRequest request);

    /**
     * 쿠폰 삭제
     */
    @DeleteMapping("/coupons/{couponId}")
    CommonResponse<Void> deleteById(@PathVariable("couponId") Long couponId);

    /**
     * 도서 ID로 조회 (페이징)
     * @param bookId 도서 ID
     * @param page   페이지 인덱스 (0부터 시작)
     * @param size   페이지 크기
     */
    @GetMapping("/coupons/by-book/{bookId}")
    CommonResponse<PageResponse<CouponResponse>> getByBookId(
            @PathVariable("bookId") Long bookId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    /**
     * 카테고리 ID로 조회 (페이징)
     * @param categoryId 카테고리 ID
     * @param page       페이지 인덱스 (0부터 시작)
     * @param size       페이지 크기
     */
    @GetMapping("/coupons/by-category/{categoryId}")
    CommonResponse<PageResponse<CouponResponse>> getByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);
}
