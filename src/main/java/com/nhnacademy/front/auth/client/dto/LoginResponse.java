package com.nhnacademy.front.auth.client.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
