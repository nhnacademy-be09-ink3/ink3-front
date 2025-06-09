package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatus;
import com.nhnacademy.front.shop.order.dto.OrderStatusUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import java.time.LocalDateTime;
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

    public void updateOrderStatus(long orderId, OrderStatusUpdateRequest orderStatusUpdateRequest){
        adminOrderClient.updateOrderStatus(orderId, orderStatusUpdateRequest);
    }

    public PageResponse<RefundResponse> getRefunds(int page, int size) {
        CommonResponse<PageResponse<RefundResponse>> refunds = adminOrderClient.getRefunds(page, size);
        return refunds.data();
    }
    public void approveRefund(long orderId, long userId) {
        adminOrderClient.approveRefund(orderId , userId);
    }

    public ShipmentResponse updateShipmentDeliveredAtToNow(long orderId) {
        return adminOrderClient.updateShipmentDeliveredAt(orderId, LocalDateTime.now()).data();
    }

    public ShipmentResponse updateShipmentDeliveredAtToNull(long orderId) {
        return adminOrderClient.updateShipmentDeliveredAt(orderId, null).data();
    }
}
