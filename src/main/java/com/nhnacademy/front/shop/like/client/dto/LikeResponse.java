package com.nhnacademy.front.shop.like.client.dto;

import com.nhnacademy.front.shop.book.dto.BookStatus;

public record LikeResponse(
        Long id,
        Long userId,
        Long bookId,
        String bookTitle,
        String bookThumbnailUrl,
        Integer originalPrice,
        Integer salePrice,
        Integer discountRate,
        BookStatus bookStatus
) {
}