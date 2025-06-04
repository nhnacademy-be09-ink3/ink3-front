package com.nhnacademy.front.shop.guest.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.guest.client.GuestOrderClient;
import com.nhnacademy.front.shop.guest.dto.GuestCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderDetailsResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderFormCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GuestOrderService {
    private final GuestOrderClient guestOrderClient;

    public GuestOrderResponse getGuestOrder(GuestCreateRequest request) {
        CommonResponse<GuestOrderResponse> guestOrder = guestOrderClient.getGuestOrder(request.getOrderId(), request.getEmail());
        return guestOrder.data();
    }

    public GuestOrderDetailsResponse getGuestOrderDetails(long orderId) {
        CommonResponse<GuestOrderDetailsResponse> guestOrderDetails = guestOrderClient.getGuestOrderDetails(orderId);
        return guestOrderDetails.data();
    }

    public PageResponse<OrderBookResponse> getOrderBookList(long orderId, Integer page, Integer size) {
        CommonResponse<PageResponse<OrderBookResponse>> orderBookListByOrderId = guestOrderClient.getOrderBookListByOrderId(orderId, page, size);
        return orderBookListByOrderId.data();
    }

    public GuestOrderResponse createGuestOrder(GuestOrderFormCreateRequest guestOrderFormCreateRequest) {
        CommonResponse<GuestOrderResponse> guestOrder = guestOrderClient.createGuestOrder(guestOrderFormCreateRequest);
        return guestOrder.data();
    }
}