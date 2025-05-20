package com.nhnacademy.front.auth.dto;

public record ClientLoginRequest(
        String id,
        String password
) {
}
