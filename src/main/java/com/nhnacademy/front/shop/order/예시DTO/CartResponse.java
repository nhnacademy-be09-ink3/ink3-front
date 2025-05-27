package com.nhnacademy.front.shop.order.예시DTO;

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
