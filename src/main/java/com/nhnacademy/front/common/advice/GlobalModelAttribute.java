package com.nhnacademy.front.common.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @Value("${gateway-base-url}")
    private String gatewayBaseUrl;

    @ModelAttribute("gatewayBaseUrl")
    public String gatewayBaseUrl() {
        return gatewayBaseUrl;
    }
}
