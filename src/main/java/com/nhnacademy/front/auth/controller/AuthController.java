package com.nhnacademy.front.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @GetMapping("/admin/login")
    public String getAdminLogin() {
        return "admin/auth/login";
    }
}
