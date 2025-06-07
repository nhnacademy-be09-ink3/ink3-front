package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorRoleRequest(
        @NotNull Long authorId,
        @NotBlank String role
) {}
