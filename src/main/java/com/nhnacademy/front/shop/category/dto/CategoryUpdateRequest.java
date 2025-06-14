package com.nhnacademy.front.shop.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateRequest(
        @NotNull Long id,
        @NotBlank String name
) {
}
