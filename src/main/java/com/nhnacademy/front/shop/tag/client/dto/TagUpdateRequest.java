package com.nhnacademy.front.shop.tag.client.dto;

import jakarta.validation.constraints.NotBlank;

public record TagUpdateRequest(
        @NotBlank
        String name
) {}