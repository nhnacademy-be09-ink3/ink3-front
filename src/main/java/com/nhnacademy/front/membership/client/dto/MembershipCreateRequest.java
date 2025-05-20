package com.nhnacademy.front.membership.client.dto;

public record MembershipCreateRequest(
        String name,
        Integer conditionAmount,
        Integer pointRate
) {
}
