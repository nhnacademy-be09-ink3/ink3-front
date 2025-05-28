package com.nhnacademy.front.shop.book.client.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(
        @NotBlank String name,
        @NotBlank String role
) {}
