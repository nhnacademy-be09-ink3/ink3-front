package com.nhnacademy.front.shop.category.client.dto;

public record CategoryFlatDto(
        Long id,
        String name,
        Long parentId,
        Integer depth
) {
}
