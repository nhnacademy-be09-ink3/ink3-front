package com.nhnacademy.front.auth.client.dto;

public record ReissueRequest(
        long id,
        UserRole role,
        String refreshToken
) {
}
