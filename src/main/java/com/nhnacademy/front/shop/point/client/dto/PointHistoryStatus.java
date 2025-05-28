package com.nhnacademy.front.shop.point.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointHistoryStatus {
    EARN("적립"),
    USE("사용"),
    CANCEL("취소");

    private final String label;
}
