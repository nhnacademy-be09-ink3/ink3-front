package com.nhnacademy.front.shop.book.dto;

import java.util.List;

public record BookPreviewResponse(
        Long id,
        String title,
        Integer originalPrice,
        Integer salePrice,
        Integer discountRate,
        String thumbnailUrl,
        List<String> authors,
        Double averageRating,
        Long reviewCount,
        Long likeCount
) {
}
