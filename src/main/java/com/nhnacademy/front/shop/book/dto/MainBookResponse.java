package com.nhnacademy.front.shop.book.dto;

import java.util.List;

public record MainBookResponse(
    Long id,
    String title,
    int originalPrice,
    int salePrice,
    int discountRate,
    String thumbnailUrl,
    boolean isPackable,
    List<String> authorNames
) {
}
