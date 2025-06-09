package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.admin.order.AdminOrderService;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatus;
import com.nhnacademy.front.shop.order.dto.OrderStatusUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin-order")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    private final OrderService orderService;

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

    // 주문 리스트 요청
    @GetMapping("/orders")
    public String orders(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageResponse<OrderResponse> orders = adminOrderService.getOrderList(page, size);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                orders.page(), orders.totalPages(), 10);
        log.info("order size = {}", orders.content().size());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orders", orders);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/order";
    }

    // 주문 상태 변경 요청
    @PostMapping("/orders/{orderId}/order-status")
    public String updateOrderStatus(@PathVariable Long orderId,
                                    @RequestParam("beforeStatus") String beforeStatus,
                                    @RequestParam("afterStatus") String afterStatus,
                                    RedirectAttributes redirectAttributes) {
        try {
            OrderStatus before = OrderStatus.valueOf(beforeStatus);
            OrderStatus after = OrderStatus.valueOf(afterStatus);

            if(after==OrderStatus.DELIVERED){
                adminOrderService.updateShipmentDeliveredAtToNow(orderId);
            }else if(before==OrderStatus.DELIVERED && ( after==OrderStatus.CREATED || after==OrderStatus.CONFIRMED)){
                adminOrderService.updateShipmentDeliveredAtToNull(orderId);
            }
            adminOrderService.updateOrderStatus(orderId, new OrderStatusUpdateRequest(after));
            return "redirect:/admin-order/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "상태 변경 실패: " + e.getMessage());
            return "redirect:/admin-order/orders";
        }
    }
}
