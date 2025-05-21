package com.nhnacademy.front.auth.client.dto;

public record JwtToken(
        String token,
        long expiresAt
) {
}
