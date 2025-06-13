package com.nhnacademy.front.shop.book.dto;

import java.time.LocalDate;

public record AdminBookResponse(
        Long id,
        String isbn,
        String title,
        String publisher,
        LocalDate publishedAt,
        int originalPrice,
        int salePrice,
        int discountRate,
        int quantity,
        boolean isPackable,
        double averageRating,
        String thumbnailUrl,
        BookStatus status
) {
}
