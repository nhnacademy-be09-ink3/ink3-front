package com.nhnacademy.front.shop.coupon.policy.client.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PolicyUpdateRequest(
        @NotBlank
        @Size(min = 1, max = 20)
        String name,
        @NotNull
        DiscountType discountType,
        @NotNull @Min(0)
        int minimumOrderAmount,

        Integer discountValue,

        Integer discountPercentage,

        Integer maximumDiscountAmount

) {
}
