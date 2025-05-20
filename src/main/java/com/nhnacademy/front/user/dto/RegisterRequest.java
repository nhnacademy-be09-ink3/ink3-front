package com.nhnacademy.front.user.dto;

import java.time.LocalDate;

public record RegisterRequest(
        String name,
        String id,
        String password,
        String email,
        String phone,
        LocalDate birthday
) {
}
