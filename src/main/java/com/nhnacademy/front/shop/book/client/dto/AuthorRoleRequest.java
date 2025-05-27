package com.nhnacademy.front.shop.book.client.dto;

public record AuthorRoleRequest(
        Long authorId,
        String role
) {}
