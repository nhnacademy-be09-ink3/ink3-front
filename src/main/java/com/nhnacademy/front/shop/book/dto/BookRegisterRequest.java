package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record BookRegisterRequest(
        AladinBookResponse aladinBookResponse,
        String contents,
        @NotNull @PositiveOrZero Integer priceSales,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull BookStatus status,
        Boolean isPackable,
        List<String> tags
) {}
