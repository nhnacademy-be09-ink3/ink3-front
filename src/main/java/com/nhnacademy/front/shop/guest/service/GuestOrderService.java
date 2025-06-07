package com.nhnacademy.front.shop.guest.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.guest.client.GuestOrderClient;
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

    public GuestOrderDetailsResponse getGuestOrderDetails(long orderId, String email) {
        CommonResponse<GuestOrderDetailsResponse> guestOrderDetails = guestOrderClient.getGuestOrderDetails(orderId, email);
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