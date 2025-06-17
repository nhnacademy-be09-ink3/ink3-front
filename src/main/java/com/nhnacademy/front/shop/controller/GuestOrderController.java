package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderDetailsResponse;
import com.nhnacademy.front.shop.guest.dto.GuestRequest;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.guest.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/guest-order")
public class GuestOrderController {
    private final GuestOrderService guestOrderService;

    // 비회원 주문 조회 로그인
    @GetMapping("/login")
    public String getGuestLogin() {
        return "order/order-guest-login";
    }

    // 비회원 주문 상세
    @PostMapping("/orders")
    public String getGuestOrders(
            Model model,
            @ModelAttribute GuestRequest guestRequest
    ) {
        try {
            GuestOrderDetailsResponse guestOrderDetails =
                    guestOrderService.getGuestOrderDetails(guestRequest.getOrderId(), guestRequest.getEmail());
            PageResponse<OrderBookResponse> orderBookList =
                    guestOrderService.getOrderBookList(guestRequest.getOrderId(), 0, 100);

            model.addAttribute("orderBookList", orderBookList);
            model.addAttribute("guestOrderDetails", guestOrderDetails);
            return "order/order-guest-tracking";
        }catch (Exception e) {
            model.addAttribute("orderNotFound", true);
            return "order/order-guest-login";
        }
    }
}
