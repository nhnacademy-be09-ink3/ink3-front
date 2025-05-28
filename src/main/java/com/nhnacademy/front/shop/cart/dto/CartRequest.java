package com.nhnacademy.front.shop.cart.dto;

public record CartRequest(
    Long userId,
    Long bookId,
    int quantity
) {
}
