package com.nhnacademy.front.shop.payment.dto;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PaymentUrlProperty {
    @Value("${toss.url.success}")
    private String successURL;

    @Value("${toss.url.fail}")
    private String failURL;

    @Value("${toss.url.success-guest}")
    private String successGuestURL;

    @Value("${toss.url.fail-guest}")
    private String failGuestURL;}
