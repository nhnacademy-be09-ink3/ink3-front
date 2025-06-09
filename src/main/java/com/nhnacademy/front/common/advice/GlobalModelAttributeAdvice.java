package com.nhnacademy.front.common.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nhnacademy.front.shop.logo.client.LogoClient;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {
    private final LogoClient logoClient;

    @ModelAttribute("logoPresignedUrl")
    public String logoPresignedUrl() {
        return logoClient.getLogoPresignedUrl();
    }
}
