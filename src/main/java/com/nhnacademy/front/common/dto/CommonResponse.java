package com.nhnacademy.front.common.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record CommonResponse<T>(
        int status,
        String message,
        LocalDateTime timestamp,
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CommonResponse<T> create(T data) {
        return CommonResponse.<T>builder()
                .status(HttpStatus.CREATED.value())
                .message(ResponseMessage.CREATED.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CommonResponse<T> update(T data) {
        return CommonResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(ResponseMessage.UPDATED.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CommonResponse<T> error(HttpStatus status, String message) {
        return CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
    }
}
