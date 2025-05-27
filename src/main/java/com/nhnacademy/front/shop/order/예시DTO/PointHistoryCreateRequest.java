package com.nhnacademy.front.shop.order.예시DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PointHistoryCreateRequest(
        @NotNull
        Integer delta,

        @NotNull
        PointHistoryStatus status,

        @NotBlank
        String description
) {
}
