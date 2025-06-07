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

        Integer discountValue,

        Integer discountPercentage,

        @Min(value = 0, message = "최대 할인 금액은 0 이상이어야 합니다.")
        Integer maximumDiscountAmount
) {
        public PolicyCreateRequest {
                if (discountValue == null) {
                        discountValue = 0;
                }
                if (discountPercentage == null) {
                        discountPercentage = 0;
                }
                // 최대 할인 금액은 넘겨준 값 그대로 사용하거나, 원한다면 비어 있으면 0으로 바꿀 수 있습니다.
                if (maximumDiscountAmount == null) {
                        maximumDiscountAmount = 0;
                }
        }
}
