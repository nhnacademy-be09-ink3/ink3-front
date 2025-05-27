package com.nhnacademy.front.shop.order.dto;

import com.nhnacademy.front.shop.orderBook.dto.OrderBookCreateRequest;
import com.nhnacademy.front.shop.shipment.dto.ShipmentCreateRequest;
import java.util.List;

public record OrderFormCreateRequest(
        OrderCreateRequest orderCreateRequest,
        ShipmentCreateRequest shipmentCreateRequest,
        List<OrderBookCreateRequest> createRequestList
) {
}
