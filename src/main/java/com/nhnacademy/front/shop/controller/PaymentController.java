package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderType;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.TossUrlProperty;
import com.nhnacademy.front.shop.payment.service.PaymentService;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final OrderService orderService;

    @PostMapping
    public String getPaymentPage(
            Model model,
            HttpServletRequest request,
            @RequestBody OrderFormCreateRequest orderFormCreateRequest) {
        UserResponse currentUser = userService.getCurrentUser();
        CommonResponse<OrderResponse> commonOrderResponse = orderClient.createOrder(orderFormCreateRequest);
        OrderResponse orderResponse = commonOrderResponse.data();
        //TODO 주문명 수정 필요
        String orderName = "임시 주문 명칭";

        OrderType orderType;
        if (orderService.isLoggedIn(request)) {
            if(orderFormCreateRequest.createRequestList().size()>1){
                orderType = OrderType.MEMBER_CART;
            }else{
                orderType = OrderType.MEMBER_BOOK;
            }
        }else{
            if(orderFormCreateRequest.createRequestList().size()>1){
                orderType = OrderType.GUEST_CART;
            }else{
                orderType = OrderType.GUEST_BOOK;
            }
        }

        model.addAttribute("orderType", orderType);
        model.addAttribute("usedPointAmount", orderFormCreateRequest.usedPointAmount());
        model.addAttribute("discountAmount", orderFormCreateRequest.discountAmount());
        model.addAttribute("amount", orderFormCreateRequest.paymentAmount());
        model.addAttribute("orderId", orderResponse.getOrderUUID());
        model.addAttribute("userId", currentUser.id());
        model.addAttribute("realOrderId", orderResponse.getId());
        model.addAttribute("orderName", orderName);
        model.addAttribute("customerName", orderFormCreateRequest.orderCreateRequest().getOrdererName());
        model.addAttribute("tossSuccessURL", tossUrlProperty.getSuccessURL());
        model.addAttribute("tossFailURL", tossUrlProperty.getFailURL());
        return "payment/toss-payment";
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
    @ResponseBody
    public ResponseEntity<String> paymentCancel(@RequestBody Map<String, String> body) {
        long orderId = Long.parseLong(body.get("orderId"));
        String returnUrl = body.get("returnUrl");
        paymentService.dealWithPaymentCancel(orderId);
        return ResponseEntity.ok(returnUrl);
    }
}
