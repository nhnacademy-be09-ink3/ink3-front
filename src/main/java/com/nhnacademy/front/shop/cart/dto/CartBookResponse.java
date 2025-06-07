package com.nhnacademy.front.shop.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartBookResponse(
    @JsonProperty("title") String bookTitle,
    @JsonProperty("originalPrice") int originalBookPrice,
    @JsonProperty("salePrice") int saleBookPrice,
    @JsonProperty("discountRate") int bookDiscountRate,
    @JsonProperty("thumbnailUrl") String thumbnailUrl,
    @JsonProperty("isPackable") boolean isPackable
) {
}
