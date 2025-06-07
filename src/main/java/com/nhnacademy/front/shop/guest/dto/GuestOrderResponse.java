package com.nhnacademy.front.shop.guest.dto;

import com.nhnacademy.front.shop.order.dto.OrderStatus;
import java.time.LocalDateTime;

public record GuestOrderResponse(
        Long orderId,
        String orderUUID,
        OrderStatus orderStatus,
        LocalDateTime orderedAt,
        String ordererName,
        String ordererPhone
) {
}
