package com.nhnacademy.front.shop.publisher.client.dto;

import jakarta.validation.constraints.NotBlank;

public record PublisherCreateRequest(
        @NotBlank
        String name
) {}
