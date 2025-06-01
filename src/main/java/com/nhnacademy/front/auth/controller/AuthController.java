package com.nhnacademy.front.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
    @Value("${gateway-base-url}")
    private String gatewayUrl;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("payco", gatewayUrl + "/auth/oauth2/authorization/payco");
        return "auth/login";
    }

    @GetMapping("/admin/login")
    public String getAdminLogin() {
        return "admin/auth/login";
    }
}
