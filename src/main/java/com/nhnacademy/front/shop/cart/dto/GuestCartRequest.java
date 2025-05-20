package com.nhnacademy.front.shop.cart.dto;

public record GuestCartRequest(
    Long bookId,
    int quantity
) {
}
