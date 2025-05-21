package com.nhnacademy.front.shop.user.client.dto;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordUpdateRequest(
        @NotBlank String currentPassword,
        @NotBlank String newPassword
) {
}
