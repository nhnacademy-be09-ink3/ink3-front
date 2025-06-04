package com.nhnacademy.front.shop.order.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundResponse {
    private Long id;
    private Long orderId;
    private String reason;
    private String details;
    private Integer refundShippingFee;
    private LocalDateTime createdAt;
    private Boolean approved;
    private Long userId;
}
