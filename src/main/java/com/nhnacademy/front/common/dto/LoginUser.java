package com.nhnacademy.front.common.dto;

import com.nhnacademy.front.auth.client.dto.UserType;

public record LoginUser(
        Long id,
        UserType type
) {
}
