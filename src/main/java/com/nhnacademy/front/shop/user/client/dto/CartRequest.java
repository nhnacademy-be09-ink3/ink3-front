package com.nhnacademy.front.cart.dto;

public record CartRequest(
    Long userId,
    Long bookId,
    int quantity
) {
}
