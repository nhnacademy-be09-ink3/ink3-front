package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
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

    // 주문 상세 페이지
    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable long orderId, Model model) {
        OrderResponse orderResponse = orderService.getOrder(orderId);
        PageResponse<OrderBookResponse> orderBookList = orderService.getOrderBookList(orderId, 0, 100);
        ShipmentResponse shipment = orderService.getShipment(orderId);

        //TODO : 실제 결제 정보로 수정
        PaymentResponse paymentResponseTest = new PaymentResponse(
                1L,
                orderId,
                500,
                2000,
                29000,
                PaymentType.TOSS,
                LocalDateTime.now(),
                LocalDateTime.now());

        model.addAttribute("shipment", shipment);
        model.addAttribute("order", orderResponse);
        model.addAttribute("orderBookList", orderBookList);
        model.addAttribute("paymentResponseTest", paymentResponseTest);
        return "order/order-details";
    }
}
