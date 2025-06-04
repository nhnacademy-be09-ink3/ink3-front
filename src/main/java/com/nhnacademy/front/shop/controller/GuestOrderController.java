package com.nhnacademy.front.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestOrderController {
    @GetMapping("/guest/login")
    public String getGuestLogin() {
        return "order/order-guest-login";
    }

    @GetMapping("/guest/orders")
    public String getGuestOrders(Model model) {
        return "order/order-guest-tracking";
    }
}
