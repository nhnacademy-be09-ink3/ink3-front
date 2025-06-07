package com.nhnacademy.front.shop.user.client.dto;

import java.time.LocalDateTime;

public record UserListItemDto(
        Long id,
        String name,
        String loginId,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt,
        UserStatus status,
        String membership,
        Integer point,
        String social
) {
}
