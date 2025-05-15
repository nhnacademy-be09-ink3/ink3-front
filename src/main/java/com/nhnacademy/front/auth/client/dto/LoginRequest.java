package com.nhnacademy.front.auth.client.dto;

public record LoginRequest(
        String username,
        String password,
        UserType type
) {
}
