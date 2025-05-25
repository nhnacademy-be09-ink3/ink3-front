package com.nhnacademy.front.shop.user.client.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String loginId,
        String name,
        String email,
        String phone,
        LocalDate birthday,
        Integer point,
        UserStatus status,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
}
