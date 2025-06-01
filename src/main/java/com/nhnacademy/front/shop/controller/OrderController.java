package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.service.PaymentService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // 주문 상세 페이지
    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable long orderId, Model model) {
        OrderResponse orderResponse = orderService.getOrder(orderId);
        PageResponse<OrderBookResponse> orderBookList = orderService.getOrderBookList(orderId, 0, 100);
        ShipmentResponse shipment = orderService.getShipment(orderId);
        PaymentResponse paymentResponse = paymentService.getPayment(orderId);

        model.addAttribute("shipment", shipment);
        model.addAttribute("order", orderResponse);
        model.addAttribute("orderBookList", orderBookList);
        model.addAttribute("paymentResponse", paymentResponse);
        return "order/order-details";
    }
}
