package com.nhnacademy.front.shop.user.client.dto;

import jakarta.validation.constraints.NotNull;

public record UserMembershipUpdateRequest(
        @NotNull Long membershipId
) {
}
