package com.nhnacademy.front.auth.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull UserType userType,
        @NotNull Boolean rememberMe
) {
}
