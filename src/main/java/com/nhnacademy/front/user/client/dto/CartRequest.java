package com.nhnacademy.front.user.client.dto;

public record CartRequest(
    Long userId,
    Long bookId,
    int quantity
) {
}
