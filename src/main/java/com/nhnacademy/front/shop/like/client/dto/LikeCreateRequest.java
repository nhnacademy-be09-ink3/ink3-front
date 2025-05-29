package com.nhnacademy.front.shop.like.client.dto;

import jakarta.validation.constraints.NotNull;

public record LikeCreateRequest(
        @NotNull Long bookId
) {
}
