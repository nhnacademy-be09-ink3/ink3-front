package com.nhnacademy.front.shop.order.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;
    // 사용자의 주문 list가져오기
    public PageResponse<OrderResponse> getOrders(Integer page, Integer size) {
        CommonResponse<PageResponse<OrderResponse>> orderListByUser = orderClient.getOrderListByUser(page, size);
        return orderListByUser.data();
    }

    // 특정 주문 정보 가져오기
    public OrderResponse getOrder(Long orderId) {
        CommonResponse<OrderResponse> order = orderClient.getOrder(orderId);
        return order.data();
    }

    // 배송 정보 가져오기
    public ShipmentResponse getShipment(Long orderId) {
        CommonResponse<ShipmentResponse> shipment = orderClient.getShipment(orderId);
        return shipment.data();
    }

    // 포장 정책 리스트 가져오기
    public PageResponse<PackagingResponse> getPackagingList(Integer page, Integer size) {
        CommonResponse<PageResponse<PackagingResponse>> packagingsResponse = orderClient.getPackagings(page,size);
        return packagingsResponse.data();
    }

    public PageResponse<OrderBookResponse> getOrderBookList(long orderId, Integer page, Integer size) {
        CommonResponse<PageResponse<OrderBookResponse>> orderBookListByOrderId = orderClient.getOrderBookListByOrderId(orderId, page, size);
        return orderBookListByOrderId.data();
    }
}
