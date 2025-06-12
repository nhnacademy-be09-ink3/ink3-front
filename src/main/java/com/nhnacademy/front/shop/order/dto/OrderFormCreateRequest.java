package com.nhnacademy.front.shop.order.dto;

import com.nhnacademy.front.shop.payment.dto.PaymentType;
import java.util.List;

public record OrderFormCreateRequest(
    // 주문 테이블
    OrderCreateRequest orderCreateRequest,
    ShipmentCreateRequest shipmentCreateRequest,
    List<OrderBookCreateRequest> createRequestList,
    int discountAmount,
    int usedPointAmount,
    int paymentAmount,
    PaymentType paymentType
){
}
