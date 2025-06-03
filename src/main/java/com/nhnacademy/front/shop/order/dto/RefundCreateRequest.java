package com.nhnacademy.front.shop.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundCreateRequest {
    @NotNull
    private Long orderId;
    @NotBlank
    @Length(max = 20)
    private String reason;
    @NotBlank
    @Length(max = 255)
    private String details;
}
