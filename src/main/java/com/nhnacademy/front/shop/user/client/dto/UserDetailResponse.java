package com.nhnacademy.front.shop.user.client.dto;

import com.nhnacademy.front.shop.membership.client.dto.MembershipResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDetailResponse(
        Long id,
        String loginId,
        String name,
        String email,
        String phone,
        LocalDate birthday,
        Integer point,
        MembershipResponse membership,
        UserStatus status,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
}
