package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.admin.order.AdminOrderService;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin-order")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/refunds")
    public String refunds(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageResponse<RefundResponse> refunds = adminOrderService.getRefunds(page, size);

        model.addAttribute("refunds", refunds);
        return "admin/order/refund";
    }

    @PostMapping("/refunds/{orderId}")
    public String approveRefund(@PathVariable Long orderId,
                                @RequestParam Long userId) {
        adminOrderService.approveRefund(orderId, userId);
        return "redirect:/admin-order/refunds";
    }


    @GetMapping("/orders")
    public String orders(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {



        return "admin/order/orders";
    }
}
