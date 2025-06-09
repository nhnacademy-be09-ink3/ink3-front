package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.TossUrlProperty;
import com.nhnacademy.front.shop.payment.dto.ZeroPaymentRequest;
import com.nhnacademy.front.shop.payment.service.PaymentService;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderClient orderClient;
    private final UserService userService;
    //TODO 확장성을 위해서 TossUrlProperty 수정해야함.
    private final TossUrlProperty tossUrlProperty;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPaymentPage(
            @RequestBody OrderFormCreateRequest orderFormCreateRequest
    ) {
        UserResponse currentUser = userService.getCurrentUser();
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();
        String orderUUID = orderResponse.getOrderUUID();
        String orderName = "도서 " + orderFormCreateRequest.createRequestList().size() + "종 외";
        String customerName = orderFormCreateRequest.orderCreateRequest().getOrdererName();

        String successUrl = tossUrlProperty.getSuccessURL()
                + "?realOrderId=" + orderResponse.getId()
                + "&usedPointAmount=" + orderFormCreateRequest.usedPointAmount()
                + "&discountAmount=" + orderFormCreateRequest.discountAmount()
                + "&userId=" + currentUser.id();

        String failUrl = tossUrlProperty.getFailURL()
                + "?orderId=" + orderResponse.getId();

        Map<String, Object> response = Map.of(
                "orderId", orderUUID,
                "orderName", orderName,
                "customerName", customerName,
                "amount", orderFormCreateRequest.paymentAmount(),
                "successUrl", successUrl,
                "failUrl", failUrl
        );
        return ResponseEntity.ok(response);
    }

    //TODO  :  민감한 정보이기 때문에 레디스 사용
    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount,
            @RequestParam("orderId") String orderUUID,
            @RequestParam("realOrderId") long orderId,
            @RequestParam("userId") long userId,
            @RequestParam("discountAmount") int discountAmount,
            @RequestParam("usedPointAmount") int usedPointAmount
    ) {
        PaymentConfirmRequest paymentConfirmRequest = PaymentConfirmRequest.builder()
                .userId(userId)
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderUUID(orderUUID)
                .discountAmount(discountAmount)
                .usedPointAmount(usedPointAmount)
                .amount(amount)
                .paymentType(PaymentType.TOSS)
                .build();
        PaymentResponse paymentResponse = paymentService.confirmPayment(paymentConfirmRequest);
        model.addAttribute("paymentResponse", paymentResponse);
        return "payment/payment-success";
    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam long orderId){
        paymentService.dealWithPaymentFail(orderId);
        return "payment/payment-fail";
    }

    @PostMapping("/cancel")
    public String paymentCancel(@RequestParam long orderId) {
        paymentService.dealWithPaymentCancel(orderId);
        return "order/order-cancel";
    }

    @PostMapping("/zero")
    public String paymentZero(
            Model model,
            @RequestBody OrderFormCreateRequest orderFormCreateRequest
    ) {
        // 주문 생성
        UserResponse currentUser = userService.getCurrentUser();
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();

        // 결제 생성
        ZeroPaymentRequest zeroPaymentRequest = ZeroPaymentRequest.builder()
                .userId(currentUser.id())
                .orderId(orderResponse.getId())
                .usedPointAmount(orderFormCreateRequest.usedPointAmount())
                .discountAmount(orderFormCreateRequest.discountAmount())
                .amount(orderFormCreateRequest.paymentAmount())
                .build();
        PaymentResponse zeroPaymentResponse = paymentService.createZeroPayment(zeroPaymentRequest);

        model.addAttribute("paymentResponse", zeroPaymentResponse);
        return "payment/payment-success";
    }
}
