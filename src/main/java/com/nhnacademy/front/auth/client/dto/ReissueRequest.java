package com.nhnacademy.front.auth.client.dto;

public record ReissueRequest(
        long id,
        UserType userType,
        String refreshToken
) {
}
