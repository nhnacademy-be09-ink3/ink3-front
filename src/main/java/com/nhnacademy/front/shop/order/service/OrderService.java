package com.nhnacademy.front.shop.order.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderWithDetailsResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.RefundCreateRequest;
import com.nhnacademy.front.shop.order.dto.RefundPolicyResponse;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;
    // 사용자의 주문 list가져오기
    public PageResponse<OrderWithDetailsResponse> getOrders(Integer page, Integer size) {
        CommonResponse<PageResponse<OrderWithDetailsResponse>> orderListByUser = orderClient.getOrderListByUser(page, size);
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

    // 주문에 대한 도서 리시트 가져오기
    public PageResponse<OrderBookResponse> getOrderBookList(long orderId, Integer page, Integer size) {
        CommonResponse<PageResponse<OrderBookResponse>> orderBookListByOrderId = orderClient.getOrderBookListByOrderId(orderId, page, size);
        return orderBookListByOrderId.data();
    }

    // 활성화된 배송 정책 가져오기
    public ShippingPolicyResponse getActiveShippingPolicy(){
        CommonResponse<ShippingPolicyResponse> activeShippingPolicy = orderClient.getActiveShippingPolicy();
        return activeShippingPolicy.data();
    }

    // 활성화된 반품 정책 가져오기
    public RefundPolicyResponse getActivateRefundPolicy(){
        CommonResponse<RefundPolicyResponse> activateRefundPolicy = orderClient.getActivateRefundPolicy();
        return activateRefundPolicy.data();
    }

    public RefundResponse createRefund(RefundCreateRequest request){
        CommonResponse<RefundResponse> refund = orderClient.createRefund(request);
        return refund.data();
    }

    // 회원/비회원 구분
    public boolean isLoggedIn(HttpServletRequest request) {
        if (request.getCookies() == null) return false;
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}
