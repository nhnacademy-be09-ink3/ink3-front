package com.nhnacademy.front.shop.membership.dto;

public record MembershipUpdateFormRequest(
        Long id,
        String name,
        Integer conditionAmount,
        Integer pointRate
) {
}
