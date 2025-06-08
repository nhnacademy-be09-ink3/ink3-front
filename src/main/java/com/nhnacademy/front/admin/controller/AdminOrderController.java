package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.admin.order.AdminOrderService;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.util.PageUtil;
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

    // 반품 페이지
    @GetMapping("/refunds")
    public String refunds(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageResponse<RefundResponse> refunds = adminOrderService.getRefunds(page, size);
        //TODO : 주문ID를 눌렀을 때 모달로 주문 상세 볼 수 있도록 해야함.
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                refunds.page(), refunds.totalPages(), 10);

        model.addAttribute("refunds", refunds);
        model.addAttribute("pageInfo", pageInfo);
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

        PageResponse<OrderResponse> orderList = adminOrderService.getOrderList(page, size);
        model.addAttribute("orders", orderList);
        return "admin/order/order";
    }
}
