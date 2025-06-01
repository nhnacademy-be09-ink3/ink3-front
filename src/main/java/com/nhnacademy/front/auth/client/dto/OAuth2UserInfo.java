package com.nhnacademy.front.auth.client.dto;

public record OAuth2UserInfo(
        String provider,
        String providerId,
        String name,
        String gender,
        String email,
        String mobile,
        String birthday
) {
}
