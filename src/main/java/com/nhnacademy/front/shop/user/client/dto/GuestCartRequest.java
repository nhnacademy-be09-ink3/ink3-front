package com.nhnacademy.front.cart.dto;

public record GuestCartRequest(
    Long bookId,
    int quantity
) {
}
