package com.nhnacademy.front.shop.book.controller.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BookStatus {
    AVAILABLE("판매중"),
    OUT_OF_STOCK("품절"),
    SOLD_OUT("판매 종료"),
    DELETED("삭제된 상품");

    private final String label;
}
