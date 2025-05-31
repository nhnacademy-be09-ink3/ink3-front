package com.nhnacademy.front.common.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @ModelAttribute("loginUser")
    public Authentication loginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return auth;
        }
        return null;
    }
}
