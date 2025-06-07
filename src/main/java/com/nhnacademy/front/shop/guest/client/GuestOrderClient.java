package com.nhnacademy.front.shop.guest.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderDetailsResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderFormCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderResponse;
import com.nhnacademy.front.shop.guest.dto.GuestResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "guestOrderClient", url = "${feign.url.shop}")
public interface GuestOrderClient {

    @GetMapping("/guest-order/{orderId}")
    CommonResponse<GuestOrderDetailsResponse> getGuestOrderDetails(
            @PathVariable long orderId,
            @RequestParam("email") String email);

    @GetMapping("/order-books/orders/{orderId}")
    CommonResponse<PageResponse<OrderBookResponse>> getOrderBookListByOrderId(
            @PathVariable("orderId") long orderId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);


    @PostMapping("/guest-order")
    CommonResponse<GuestOrderResponse> createGuestOrder(GuestOrderFormCreateRequest guestOrderFormCreateRequest);
}
