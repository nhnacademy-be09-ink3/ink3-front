package com.nhnacademy.front.shop.couponStore.service;

import com.nhnacademy.front.shop.couponStore.client.dto.CouponStoreDto;
import java.util.List;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.couponStore.client.dto.CouponStoreClient;
import com.nhnacademy.front.shop.couponStore.client.dto.CouponStoreResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponStoreService {
    private final CouponStoreClient couponStoreClient;

    public PageResponse<CouponStoreResponse> getCurrentUserCoupons(int page, int size, String sort) {
        return couponStoreClient.getStoresByUserId(page, size, sort).data();
    }

    public PageResponse<CouponStoreResponse> getCurrentUserUsedOrExpiredCoupons(int page, int size, String sort) {
        return couponStoreClient.getUsedExpiredStores(page, size, sort).data();
    }

    public PageResponse<CouponStoreResponse> getCurrentUserUnusedCoupons(int page, int size, String sort) {
        return couponStoreClient.getUnusedStores(page, size, sort).data();
    }

    public List<CouponStoreDto> getApplicableCoupons(long userId, long bookId) {
        return couponStoreClient.getApplicableCoupons(userId, bookId).data();
    }
}
