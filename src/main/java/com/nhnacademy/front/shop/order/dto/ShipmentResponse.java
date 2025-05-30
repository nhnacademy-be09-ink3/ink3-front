package com.nhnacademy.front.shop.order.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ShipmentResponse {
    private Long id;
    private Long orderId;
    private LocalDate preferredDeliveryDate;
    private LocalDateTime deliveredAt;
    private String recipientName;
    private String recipientPhone;
    private Integer postalCode;
    private String defaultAddress;
    private String detailAddress;
    private String extraAddress;
    private Integer shippingFee;
    private String shippingCode;
}
