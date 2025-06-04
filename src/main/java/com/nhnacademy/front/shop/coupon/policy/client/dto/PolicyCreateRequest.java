package com.nhnacademy.front.shop.coupon.policy.client.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PolicyCreateRequest(
        @NotBlank
        @Size(min = 1, max = 20, message = "이름은 1~20자여야 합니다.")
        String name,

        @NotNull
        @Min(value = 0, message = "최소 주문 금액은 0 이상이어야 합니다.")
        Integer minimumOrderAmount,

        @NotNull
        DiscountType discountType,

        @NotNull
        Integer discountValue,

        @NotNull
        Integer discountPercentage,

        @NotNull
        @Min(value = 0, message = "최대 할인 금액은 0 이상이어야 합니다.")
        Integer maximumDiscountAmount
) {}
