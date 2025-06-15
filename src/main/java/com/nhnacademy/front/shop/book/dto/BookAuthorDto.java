package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotBlank;

public record BookAuthorDto(
        @NotBlank String name,
        @NotBlank String role
) {}
