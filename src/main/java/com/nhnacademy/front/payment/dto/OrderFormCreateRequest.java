package com.nhnacademy.front.payment.dto;

import com.nhnacademy.front.payment.dto.orderBookDto.OrderBookListCreateRequest;
import com.nhnacademy.front.payment.dto.orderDto.OrderCreateRequest;
import com.nhnacademy.front.payment.dto.shipmentDto.ShipmentCreateRequest;

public record OrderFormCreateRequest(
        OrderCreateRequest orderCreateRequest,
        ShipmentCreateRequest shipmentCreateRequest,
        OrderBookListCreateRequest orderBookListCreateRequest
) {

}
