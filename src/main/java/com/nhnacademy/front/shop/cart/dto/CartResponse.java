package com.nhnacademy.front.shop.cart.dto;

public record CartResponse(
    Long id,
    Long userId,
    Long bookId,
    String bookTitle,
    int originalBookPrice,
    int saleBookPrice,
    int bookDiscountRate,
    String thumbnailUrl,

    int quantity
) {
}
