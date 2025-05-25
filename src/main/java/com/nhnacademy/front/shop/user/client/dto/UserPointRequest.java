package com.nhnacademy.front.shop.user.client.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserPointRequest(
        @NotNull @Min(1) Integer amount
) {
}
