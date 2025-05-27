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
    private final TossUrlProperty tossUrlProperty;

    @GetMapping
    public String getPaymentPage(
            Model model,
            @RequestBody OrderFormCreateRequest orderFormCreateRequest,
            @RequestParam int discountAmount,
            @RequestParam int usedPointAmount,
            @RequestParam int paymentAmount) {

        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();

        //TODO 사용자 정보 가져오기 + 주문 이름 정하기
        model.addAttribute("amount", paymentAmount);
        model.addAttribute("orderId", orderResponse.getOrderUUID());
        model.addAttribute("orderName", "... 도서 외 ...권");
        model.addAttribute("customerName", "홍길동");
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("usedPointAmount", usedPointAmount);
        model.addAttribute("tossSuccessURL", tossUrlProperty.getSuccessURL());
        model.addAttribute("tossFailURL", tossUrlProperty.getFailURL());
        return "payment/toss-payment";
    }


    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderUUID,
            @RequestParam("amount") int amount,
            @RequestParam("discountAmount") int discountAmount,
            @RequestParam("usedPointAmount") int usedPointAmount
    ) {
        //TODO : 결제 수단은 어떻게 알지?
        PaymentConfirmRequest paymentConfirmRequest = new PaymentConfirmRequest(
                4L,
                0L,
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
