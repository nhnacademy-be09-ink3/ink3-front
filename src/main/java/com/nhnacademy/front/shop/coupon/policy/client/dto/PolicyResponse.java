package com.nhnacademy.front.shop.coupon.policy.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PolicyResponse(
        @NotNull
        Long policyId,

        @NotBlank
        String policyName,

        @NotBlank
        int minimumOrderAmount,

        @NotNull
        DiscountType discountType,

        int discountValue,

        int discountPercentage,

        int maximumDiscountAmount,

        LocalDateTime createdAt
)  {
}
