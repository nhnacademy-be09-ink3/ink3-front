package com.nhnacademy.front.shop.category.client.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String name,
        Long parentId
) {
}
