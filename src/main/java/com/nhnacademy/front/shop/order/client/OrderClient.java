package com.nhnacademy.front.shop.order.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderWithDetailsResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderClient", url = "${feign.url.shop}")
public interface OrderClient {
    @PostMapping("/orders")
    CommonResponse<OrderResponse> createOrder(@RequestBody OrderFormCreateRequest request);

    @GetMapping("/orders/{orderId}")
    CommonResponse<OrderResponse> getOrder(@PathVariable("orderId") long orderId);

    @GetMapping("/shipments/{orderId}")
    CommonResponse<ShipmentResponse> getShipment(@PathVariable("orderId") long orderId);

    @GetMapping("/orders/me")
    CommonResponse<PageResponse<OrderWithDetailsResponse>> getOrderListByUser(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/order-books/orders/{orderId}")
    CommonResponse<PageResponse<OrderBookResponse>> getOrderBookListByOrderId(
            @PathVariable("orderId") long orderId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/packagings/activate")
    CommonResponse<PageResponse<PackagingResponse>> getPackagings(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @GetMapping("/shipping-policies/activate")
    CommonResponse<ShippingPolicyResponse> getActiveShippingPolicy();
}
