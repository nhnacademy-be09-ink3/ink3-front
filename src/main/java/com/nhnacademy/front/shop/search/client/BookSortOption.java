package com.nhnacademy.front.shop.search.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSortOption {
    POPULARITY("인기도"),
    NEWEST("신상품"),
    LOWEST_PRICE("최저가"),
    HIGHEST_PRICE("최고가"),
    RATING("평점순"),
    REVIEW("리뷰순");

    private final String label;
}
