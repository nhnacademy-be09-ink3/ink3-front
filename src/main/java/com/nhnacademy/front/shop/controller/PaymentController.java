package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.payment.client.PaymentClient;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.TossUrlProperty;
import com.nhnacademy.front.shop.user.client.UserClient;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentClient paymentClient;
    private final OrderClient orderClient;
    private final UserClient userClient;
    private final TossUrlProperty tossUrlProperty;

    @PostMapping
    public String getPaymentPage(
            Model model,
            @RequestBody OrderFormCreateRequest orderFormCreateRequest) {
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();

        //TODO 주문명 수정 필요
        String orderName = "임시 주문 명칭";

        model.addAttribute("usedPointAmount", orderFormCreateRequest.usedPointAmount());
        model.addAttribute("discountAmount", orderFormCreateRequest.discountAmount());
        model.addAttribute("amount", orderFormCreateRequest.paymentAmount());
        model.addAttribute("orderId", orderResponse.getOrderUUID());
        model.addAttribute("realOrderId", orderResponse.getOrderUUID());
        model.addAttribute("orderName", orderName);
        model.addAttribute("customerName", orderFormCreateRequest.orderCreateRequest().getOrdererName());
        model.addAttribute("tossSuccessURL", tossUrlProperty.getSuccessURL());
        model.addAttribute("tossFailURL", tossUrlProperty.getFailURL());
        return "payment/toss-payment";
    }


    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") long orderId,
            @RequestParam("orderUUID") String orderUUID,
            @RequestParam("amount") int amount,
            @RequestParam("discountAmount") int discountAmount,
            @RequestParam("usedPointAmount") int usedPointAmount
    ) {
        CommonResponse<UserResponse> currentUser = userClient.getCurrentUser();
        UserResponse user = currentUser.data();

        PaymentConfirmRequest paymentConfirmRequest = new PaymentConfirmRequest(
                user.id(),
                orderId,
                paymentKey,
                orderUUID,
                discountAmount,
                usedPointAmount,
                amount,
                PaymentType.TOSS
        );
        CommonResponse<PaymentResponse> paymentResponseCommonResponse = paymentClient.confirmPayment(paymentConfirmRequest);
        PaymentResponse paymentResponse = paymentResponseCommonResponse.data();

        // 성공 화면으로 이동
        return "payment/payment-success";
    }


    @GetMapping("/fail")
    public String paymentFail(){
        //TODO 실패했을 때 SHOP Server에 되돌리는 요청해야함

        return "payment/payment-fail";
    }
}
