package com.nhnacademy.front.shop.category.client.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryChangeParentRequest(
        @NotNull Long parentId
) {
}
