package com.nhnacademy.front.shop.book.dto;

import com.nhnacademy.front.shop.category.client.dto.CategoryFlatDto;
import java.time.LocalDate;
import java.util.List;

public record BookDetailResponse(
        Long id,
        String isbn,
        String title,
        String contents,
        String description,
        String publisherName,
        LocalDate publishedAt,
        Integer originalPrice,
        Integer salePrice,
        Integer discountRate,
        Integer quantity,
        Boolean isPackable,
        String thumbnailUrl,
        List<List<CategoryFlatDto>> categories,
        List<BookAuthorDto> authors,
        List<String> tags,
        Double averageRating,
        Long reviewCount,
        Long likeCount,
        BookStatus status
) {
}
