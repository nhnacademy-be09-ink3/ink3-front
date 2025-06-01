package com.nhnacademy.front.shop.book.dto;

import java.util.List;

public record BookRegisterRequest(
        AladinBookResponse aladinBookResponse,
        Integer priceSales,
        Integer quantity,
        BookStatus status,
        boolean isPackable,
        List<Long> tagIds
) {}
