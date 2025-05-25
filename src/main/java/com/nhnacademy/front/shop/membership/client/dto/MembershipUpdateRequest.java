package com.nhnacademy.front.shop.membership.client.dto;

public record MembershipUpdateRequest(
        String name,
        Integer conditionAmount,
        Integer pointRate
) {
}
