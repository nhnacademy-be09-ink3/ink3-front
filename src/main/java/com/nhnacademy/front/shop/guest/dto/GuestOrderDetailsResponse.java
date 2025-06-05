package com.nhnacademy.front.shop.guest.dto;

import com.nhnacademy.front.shop.order.dto.OrderStatus;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GuestOrderDetailsResponse {
    private Long orderId;
    private String orderUUId;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private String ordererName;
    private String ordererPhone;
    private LocalDate preferredDeliveryDate;
    private LocalDateTime deliveredAt;
    private String recipientName;
    private String recipientPhone;
    private Integer postalCode;
    private String defaultAddress;
    private String extraAddress;
    private Integer shippingFee;
    private String shippingCode;
    private Integer paymentAmount;
    private PaymentType paymentType;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
}
