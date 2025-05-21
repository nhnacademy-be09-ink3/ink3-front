package com.nhnacademy.front.auth.client.dto;

public record LoginResponse(
        JwtToken accessToken,
        JwtToken refreshToken
) {
}
