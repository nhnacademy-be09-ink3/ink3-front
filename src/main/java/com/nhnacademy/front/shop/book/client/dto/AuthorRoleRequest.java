package com.nhnacademy.front.shop.book.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorRoleRequest(
        @NotNull Long authorId,
        @NotBlank String role
) {}
