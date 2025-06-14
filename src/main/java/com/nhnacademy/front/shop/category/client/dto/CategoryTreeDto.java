package com.nhnacademy.front.shop.category.client.dto;

import java.util.List;

public record CategoryTreeDto(
        Long id,
        String name,
        List<CategoryTreeDto> children
) {
}
