package com.nhnacademy.front.shop.membership.client.dto;

import java.time.LocalDateTime;

public record MembershipResponse(
        Long id,
        String name,
        Integer conditionAmount,
        Integer pointRate,
        Boolean isActive,
        Boolean isDefault,
        LocalDateTime createdAt
) {
}
