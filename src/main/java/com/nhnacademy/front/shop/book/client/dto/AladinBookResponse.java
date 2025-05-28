package com.nhnacademy.front.shop.book.client.dto;

public record AladinBookResponse(
        String title,
        String description,
        String toc,
        String author,
        String publisher,
        String pubDate,
        String isbn13,
        int priceStandard,
        String cover,
        String categoryName
) {}
