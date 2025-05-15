package com.nhnacademy.front.payment.controller;

import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping("/hello")
    public String getMainPage(){
        return "payment/index";
    }

    @GetMapping
    public String getPaymentPage(Model model){
        // 주문 , 주문-도서 , 배송 repo

        // 여기서 shop 서버에서 order_UUID값을 가져와야함.
        model.addAttribute("amount", 1000);
        model.addAttribute("orderId", "order-dsnadsadsa");
        model.addAttribute("orderName", "스프링의 정석");
        model.addAttribute("customerName", "홍길동");

        return "payment/toss-payment";
    }


    @GetMapping("/success")
    public String paymentSuccess(
            Model model,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") int amount){

        // 여기서 토스 결제 승인 요청을 보내고 DB를 저장하기 위해 shop 서버로 이동해야함.
        //

        // 성공 화면으로 이동
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderUUID", orderId);
        model.addAttribute("amount", amount);
        return "payment/success";
    }

    @GetMapping("/fail")
    public String paymentFail(Model model){
        model.addAttribute("amount", 1000);
        model.addAttribute("orderId", "order-001");
        model.addAttribute("orderName", "스프링 부트 입문");
        model.addAttribute("customerName", "홍길동");

        return "payment/toss-payment";
    }
}
