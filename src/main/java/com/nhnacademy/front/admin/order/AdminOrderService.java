package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.config.client.ConfigServerHealthIndicator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminOrderService {
    private final AdminOrderClient adminOrderClient;

    public PageResponse<OrderResponse> getOrderList(int page, int size){
        CommonResponse<PageResponse<OrderResponse>> orderResponse = adminOrderClient.getOrders(page, size);
        return orderResponse.data();
    }

    public PageResponse<RefundResponse> getRefunds(int page, int size) {
        CommonResponse<PageResponse<RefundResponse>> refunds = adminOrderClient.getRefunds(page, size);
        return refunds.data();
    }
    public void approveRefund(long orderId, long userId) {
        adminOrderClient.approveRefund(orderId , userId);
    }


}
