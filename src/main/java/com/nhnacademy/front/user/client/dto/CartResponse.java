package com.nhnacademy.front.user.client.dto;

public record CartResponse(
    Long id,
    Long userId,
    Long bookId,
    int quantity
) {
}
