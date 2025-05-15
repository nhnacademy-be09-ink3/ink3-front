package com.nhnacademy.front.membership.client.dto;

public record MembershipUpdateRequest(
        String name,
        Integer conditionAmount,
        Integer pointRate
) {
}
