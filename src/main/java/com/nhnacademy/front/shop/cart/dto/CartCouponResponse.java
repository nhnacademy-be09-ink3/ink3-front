package com.nhnacademy.front.shop.cart.dto;

import com.nhnacademy.front.shop.couponStore.dto.CouponStoreDto;
import java.util.List;

public record CartCouponResponse(
    Long id,
    Long userId,
    Long bookId,
    String bookTitle,
    int originalBookPrice,
    int saleBookPrice,
    int bookDiscountRate,
    String thumbnailUrl,
    boolean isPackable,
    int quantity,
    List<CouponStoreDto> applicableCoupons
) {
}
