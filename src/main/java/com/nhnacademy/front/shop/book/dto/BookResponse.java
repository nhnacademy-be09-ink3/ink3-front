package com.nhnacademy.front.shop.book.dto;

import java.time.LocalDate;
import java.util.List;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        String contents,
        String description,
        String publisherName,
        LocalDate publishedAt,
        int originalPrice,
        int salePrice,
        int discountRate,
        int quantity,
        BookStatus status,
        boolean isPackable,
        String thumbnailUrl,
        List<String> categoryNames,
        List<String> authorNames,
        List<String> tagNames
) {
}
