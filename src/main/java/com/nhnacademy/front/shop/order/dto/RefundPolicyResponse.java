package com.nhnacademy.front.shop.order.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefundPolicyResponse {
    private Long id;
    private String name;
    private Integer returnDeadLine;
    private Integer defectReturnDeadLine;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private Integer refundShippingFee;
}
