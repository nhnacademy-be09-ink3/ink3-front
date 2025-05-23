package com.nhnacademy.front.shop.cart.dto;

public record GuestCartView(
    Long bookId,
    int quantity,
    CartBookResponse book
) {
}
