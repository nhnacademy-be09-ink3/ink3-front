package com.nhnacademy.front.shop.category.client.dto;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        Long parentId,
        List<CategoryResponse> children
) {
}