package com.nhnacademy.front.user.client.dto;

import jakarta.validation.constraints.NotNull;

public record UserMembershipUpdateRequest(
        @NotNull Long membershipId
) {
}
