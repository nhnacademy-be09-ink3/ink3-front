package com.nhnacademy.front.shop.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @NotNull
    private Integer refundShippingFee;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private Boolean approved;
}
