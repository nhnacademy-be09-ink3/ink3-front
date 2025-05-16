package com.nhnacademy.front.user.client.dto;

public record GuestCartRequest(
    Long bookId,
    int quantity
) {
}
