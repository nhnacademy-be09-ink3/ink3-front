package com.nhnacademy.front.shop.category.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryDeleteRequest(
        @NotNull Long id
) {
}
