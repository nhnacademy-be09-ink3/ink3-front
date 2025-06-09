package com.nhnacademy.front.shop.book.dto;

import java.util.Arrays;

public enum SortType {
    REVIEW,
    LIKE,
    TITLE;

    public static SortType from(String value) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(REVIEW);
    }
}
