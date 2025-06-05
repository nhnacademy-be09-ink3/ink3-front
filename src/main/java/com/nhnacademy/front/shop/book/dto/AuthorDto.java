package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(
        @NotBlank Long authorId,
        @NotBlank String authorName,
        @NotBlank String role
) {}
