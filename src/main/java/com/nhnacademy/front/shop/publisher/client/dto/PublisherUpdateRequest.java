package com.nhnacademy.front.shop.publisher.client.dto;

import jakarta.validation.constraints.NotBlank;

public record PublisherUpdateRequest(
        @NotBlank
        String name
) {}
