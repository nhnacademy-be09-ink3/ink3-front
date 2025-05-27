package com.nhnacademy.front.shop.book.client.dto;

import java.time.LocalDate;
import java.util.List;

public record BookCreateRequest(
        String isbn,
        String title,
        String contents,
        String description,
        LocalDate publishedAt,
        Integer originalPrice,
        Integer salePrice,
        Integer quantity,
        BookStatus status,
        boolean isPackable,
        String thumbnailUrl,
        Long publisherId,
        List<Long> categoryIds,
        List<AuthorRoleRequest> authors,
        List<Long> tagIds
) {}
