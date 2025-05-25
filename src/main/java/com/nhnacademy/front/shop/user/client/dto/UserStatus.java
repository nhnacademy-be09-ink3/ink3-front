package com.nhnacademy.front.shop.user.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE,
    DORMANT,
    WITHDRAWN
}
