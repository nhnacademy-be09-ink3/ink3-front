package com.nhnacademy.front.shop.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderBookResponse {
    private Long id;
    private Long orderId;
    private Long bookId;
    private Long packagingId;
    private Long couponId;
    private String bookName;
    private Integer bookSalePrice;
    private String thumbnailUrl;
    private String packagingName;
    private Integer packagingPrice;
    private String couponName;
    private Integer price;
    private Integer quantity;
}
