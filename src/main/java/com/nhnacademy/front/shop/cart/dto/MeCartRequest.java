package com.nhnacademy.front.shop.cart.dto;

public record MeCartRequest(
    Long bookId,
    int quantity
) {
}
