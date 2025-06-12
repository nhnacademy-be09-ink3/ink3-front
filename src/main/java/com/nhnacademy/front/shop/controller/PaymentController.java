package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentCancelRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.PaymentUrlProperty;
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
    private final PaymentUrlProperty tossUrlProperty;
    private final PaymentService paymentService;
    private final OrderClient orderClient;
    private final CartClient cartClient;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPaymentPage(
            @RequestBody OrderFormCreateRequest orderFormCreateRequest
    ) {
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();
        String orderUUID = orderResponse.getOrderUUID();
        String orderName = "도서 " + orderFormCreateRequest.createRequestList().size() + "종 외";
        String customerName = orderFormCreateRequest.orderCreateRequest().getOrdererName();

        String successUrl = tossUrlProperty.getSuccessURL()
                + "?realOrderId=" + orderResponse.getId()
                + "&usedPointAmount=" + orderFormCreateRequest.usedPointAmount()
                + "&discountAmount=" + orderFormCreateRequest.discountAmount()
                + "&userId=" + orderFormCreateRequest.orderCreateRequest().getUserId()
                + "&paymentType=" + orderFormCreateRequest.paymentType().name();

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
    //TODO : 바로 구매 시 장바구니 안비움
    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount,
            @RequestParam("orderId") String orderUUID,
            @RequestParam("realOrderId") long orderId,
            @RequestParam("userId") long userId,
            @RequestParam("discountAmount") int discountAmount,
            @RequestParam("usedPointAmount") int usedPointAmount,
            @RequestParam("paymentType") String paymentType
    ) {
        PaymentConfirmRequest paymentConfirmRequest = PaymentConfirmRequest.builder()
                .userId(userId)
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderUUID(orderUUID)
                .discountAmount(discountAmount)
                .usedPointAmount(usedPointAmount)
                .amount(amount)
                .paymentType(PaymentType.valueOf(paymentType))
                .build();
        PaymentResponse paymentResponse = paymentService.confirmPayment(paymentConfirmRequest);
        cartClient.deleteCarts();
        model.addAttribute("paymentResponse", paymentResponse);
        return "payment/payment-success";
    }

    /**
     * 0원 결제일 경우 처리
     * @param model
     * @param orderFormCreateRequest
     * @return
     */
    //TODO : 바로 구매 시 장바구니 안비움
    @PostMapping("/zero")
    public String paymentZero(
            Model model,
            @RequestBody OrderFormCreateRequest orderFormCreateRequest
    ) {
        // 주문 생성
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();

        // 결제 생성
        PaymentConfirmRequest paymentConfirmRequest = PaymentConfirmRequest.builder()
                .userId(orderResponse.getUserId())
                .orderId(orderResponse.getId())
                .paymentKey(null)
                .orderUUID(orderResponse.getOrderUUID())
                .discountAmount(orderFormCreateRequest.discountAmount())
                .usedPointAmount(orderFormCreateRequest.usedPointAmount())
                .amount(orderFormCreateRequest.paymentAmount())
                .paymentType(PaymentType.valueOf(orderFormCreateRequest.paymentType().name()))
                .build();
        PaymentResponse paymentResponse = paymentService.confirmPayment(paymentConfirmRequest);
        cartClient.deleteCarts();
        model.addAttribute("paymentResponse", paymentResponse);
        return "payment/payment-success";
    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam long orderId){
        paymentService.dealWithPaymentFail(orderId);
        return "payment/payment-fail";
    }

    @PostMapping("/cancel")
    public String paymentCancel(
            @RequestParam long orderId,
            @RequestBody  PaymentCancelRequest cancelRequest) {
        paymentService.dealWithPaymentCancel(orderId, cancelRequest);
        return "order/order-cancel";
    }
}
