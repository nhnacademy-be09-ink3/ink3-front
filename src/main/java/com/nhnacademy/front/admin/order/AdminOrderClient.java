package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatusUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import java.time.LocalDateTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adminOrderClient", url = "${feign.url.shop}")
public interface AdminOrderClient {
    @GetMapping("/orders")
    CommonResponse<PageResponse<OrderResponse>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PatchMapping("/orders/{orderId}/order-status")
    CommonResponse<Void> updateOrderStatus(@PathVariable long orderId,
                                           @RequestBody OrderStatusUpdateRequest request);

    @GetMapping("/refunds")
    CommonResponse<PageResponse<RefundResponse>> getRefunds(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PostMapping("/refunds/{orderId}/users/{userId}")
    CommonResponse<Void> approveRefund(
            @PathVariable("orderId") long orderId,
            @PathVariable("userId") long userId);

    @PostMapping("/refunds/{orderId}")
    CommonResponse<Void> approveRefund(@PathVariable("orderId") String orderId);

    @PatchMapping("/shipments/{orderId}/deliverea-at")
    CommonResponse<ShipmentResponse> updateShipmentDeliveredAt(
            @PathVariable("orderId") long orderId,
            @RequestParam LocalDateTime deliveredAt);
}
