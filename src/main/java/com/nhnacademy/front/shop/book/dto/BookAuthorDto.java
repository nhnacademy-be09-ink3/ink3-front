package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookAuthorDto(
        @NotBlank String name,
        @NotBlank String role
) {}
