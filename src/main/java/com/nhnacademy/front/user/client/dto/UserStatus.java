package com.nhnacademy.front.user.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE,
    DORMANT,
    WITHDRAWN
}
