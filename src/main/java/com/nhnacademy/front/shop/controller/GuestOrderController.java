package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderDetailsResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderFormCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderResponse;
import com.nhnacademy.front.shop.guest.dto.GuestPaymentConfirmRequest;
import com.nhnacademy.front.shop.guest.dto.GuestRequest;
import com.nhnacademy.front.shop.guest.service.GuestPaymentService;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import com.nhnacademy.front.shop.guest.service.GuestOrderService;
import com.nhnacademy.front.shop.payment.dto.PaymentResponse;
import com.nhnacademy.front.shop.payment.dto.PaymentType;
import com.nhnacademy.front.shop.payment.dto.TossUrlProperty;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/guest")
public class GuestOrderController {
    private final GuestOrderService guestOrderService;
    private final GuestPaymentService guestPaymentService;
    private final TossUrlProperty tossUrlProperty;

    // 비회원 주문 조회 로그인
    @GetMapping("/login")
    public String getGuestLogin() {
        return "order/order-guest-login";
    }

    // 비회원 주문 상세
    @PostMapping("/orders")
    public String getGuestOrders(
            Model model,
            @ModelAttribute GuestRequest guestRequest
    ) {
        try {
            // 배송 정보 및 주문 정보 및 상품 상세 등 정보를 가져와야함.
            GuestOrderDetailsResponse guestOrderDetails =
                    guestOrderService.getGuestOrderDetails(guestRequest.getOrderId(), guestRequest.getEmail());
            PageResponse<OrderBookResponse> orderBookList =
                    guestOrderService.getOrderBookList(guestRequest.getOrderId(), 0, 100);

            model.addAttribute("orderBookList", orderBookList);
            model.addAttribute("guestOrderDetails", guestOrderDetails);
            return "order/order-guest-tracking";
        }catch (Exception e) {
            // 모달로 띄워주면 좋을 거 같음.
            model.addAttribute("orderNotFound", true);
            return "order/order-guest-login";
        }
    }


    // 결제
    @PostMapping("/payments")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPaymentPage(
            @RequestBody GuestOrderFormCreateRequest guestOrderFormCreateRequest
    ) {

        GuestOrderResponse guestOrderResponse = guestOrderService.createGuestOrder(guestOrderFormCreateRequest);
        String orderUUID = guestOrderResponse.orderUUID();
        String orderName = "도서 " + guestOrderFormCreateRequest.createRequestList().size() + "종 외";
        String customerName = guestOrderFormCreateRequest.guestOrderCreateRequest().getOrdererName();
        String email = guestOrderFormCreateRequest.guestCreateRequest().getEmail();

        String successUrl = tossUrlProperty.getSuccessGuestURL()
                + "?realOrderId=" + guestOrderResponse.orderId()
                + "&email=" + email;

        String failUrl = tossUrlProperty.getFailGuestURL()
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
    @GetMapping("/payments/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount,
            @RequestParam("orderId") String orderUUID,
            @RequestParam("realOrderId") long orderId,
            @RequestParam("email") String email
    ) {
        GuestPaymentConfirmRequest guestPaymentConfirmRequest = GuestPaymentConfirmRequest.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderUUID(orderUUID)
                .amount(amount)
                .paymentType(PaymentType.TOSS)
                .build();
        PaymentResponse paymentResponse = guestPaymentService.confirmPayment(guestPaymentConfirmRequest);
        model.addAttribute("paymentResponse", paymentResponse);
        model.addAttribute("email", email);
        return "payment/payment-guest-success";
    }

    // 결제 실패
    @GetMapping("/payments/fail")
    public String paymentFail(@RequestParam long orderId){
        guestPaymentService.dealWithPaymentFail(orderId);
        return "payment/payment-fail";
    }
}
