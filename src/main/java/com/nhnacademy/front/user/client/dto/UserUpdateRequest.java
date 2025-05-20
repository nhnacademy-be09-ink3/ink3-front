package com.nhnacademy.front.user.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserUpdateRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotNull LocalDate birthday
) {
}
