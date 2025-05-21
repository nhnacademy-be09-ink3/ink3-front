package com.nhnacademy.front.shop.membership.client.dto;

public record MembershipCreateRequest(
        String name,
        Integer conditionAmount,
        Integer pointRate
) {
}
