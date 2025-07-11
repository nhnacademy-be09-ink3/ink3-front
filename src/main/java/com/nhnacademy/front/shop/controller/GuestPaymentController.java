package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.shop.guest.dto.GuestOrderFormCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderResponse;
import com.nhnacademy.front.shop.guest.dto.GuestPaymentConfirmRequest;
import com.nhnacademy.front.shop.guest.service.GuestOrderService;
import com.nhnacademy.front.shop.guest.service.GuestPaymentService;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.PaymentUrlProperty;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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
@RequestMapping("/guest-payment")
public class GuestPaymentController {
    private final GuestOrderService guestOrderService;
    private final GuestPaymentService guestPaymentService;
    private final PaymentUrlProperty paymentUrlProperty;

    // 결제
    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPaymentPage(
            @RequestBody GuestOrderFormCreateRequest guestOrderFormCreateRequest
    ) {

        GuestOrderResponse guestOrderResponse = guestOrderService.createGuestOrder(guestOrderFormCreateRequest);
        String orderUUID = guestOrderResponse.orderUUID();
        String orderName = "도서 " + guestOrderFormCreateRequest.createRequestList().size() + "종 외";
        String customerName = guestOrderFormCreateRequest.guestOrderCreateRequest().getOrdererName();
        String email = guestOrderFormCreateRequest.guestCreateRequest().getEmail();

        String successUrl = paymentUrlProperty.getSuccessGuestURL()
                + "?realOrderId=" + guestOrderResponse.orderId()
                + "&email=" + email
                + "&paymentType=" + guestOrderFormCreateRequest.paymentType();

        String failUrl = paymentUrlProperty.getFailGuestURL()
                + "?orderId=" + guestOrderResponse.orderUUID();

        Map<String, Object> response = Map.of(
                "orderId", orderUUID,
                "orderName", orderName,
                "customerName", customerName,
                "amount", guestOrderFormCreateRequest.paymentAmount(),
                "successUrl", successUrl,
                "failUrl", failUrl
        );
        return ResponseEntity.ok(response);
    }

    // 결제 성공
    //TODO  :  민감한 정보이기 때문에 레디스 사용
    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            HttpServletResponse response,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount,
            @RequestParam("orderId") String orderUUID,
            @RequestParam("realOrderId") long orderId,
            @RequestParam("email") String email,
            @RequestParam("paymentType") String paymentType
    ) {
        GuestPaymentConfirmRequest guestPaymentConfirmRequest = GuestPaymentConfirmRequest.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderUUID(orderUUID)
                .amount(amount)
                .paymentType(PaymentType.valueOf(paymentType))
                .build();
        PaymentResponse paymentResponse = guestPaymentService.confirmPayment(guestPaymentConfirmRequest);
        ResponseCookie deleteCookie = ResponseCookie.from("guest_cart", "")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", deleteCookie.toString());
        model.addAttribute("paymentResponse", paymentResponse);
        model.addAttribute("email", email);
        return "payment/payment-guest-success";
    }

    // 결제 실패
    @GetMapping("/fail")
    public String paymentFail(@RequestParam long orderId){
        guestPaymentService.dealWithPaymentFail(orderId);
        return "payment/payment-fail";
    }
}
