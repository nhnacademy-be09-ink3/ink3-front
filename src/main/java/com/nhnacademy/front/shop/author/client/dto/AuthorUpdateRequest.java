package com.nhnacademy.front.shop.author.client.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorUpdateRequest (
        @NotBlank String name
) {}
