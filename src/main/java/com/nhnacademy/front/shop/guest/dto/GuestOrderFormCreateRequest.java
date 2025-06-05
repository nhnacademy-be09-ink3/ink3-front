package com.nhnacademy.front.shop.guest.dto;

import com.nhnacademy.front.shop.order.dto.OrderBookCreateRequest;
import com.nhnacademy.front.shop.order.dto.ShipmentCreateRequest;
import java.util.List;

public record GuestOrderFormCreateRequest(
    GuestCreateRequest guestCreateRequest,
    GuestOrderCreateRequest guestOrderCreateRequest,
    ShipmentCreateRequest shipmentCreateRequest,
    List<OrderBookCreateRequest> createRequestList,
    int paymentAmount
){
}
