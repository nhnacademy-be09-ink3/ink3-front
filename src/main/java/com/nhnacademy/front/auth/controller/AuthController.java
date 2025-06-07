package com.nhnacademy.front.auth.controller;

import com.nhnacademy.front.auth.client.dto.OAuth2UserInfo;
import com.nhnacademy.front.common.dto.FlashMessageDto;
import com.nhnacademy.front.shop.user.dto.RegisterRequest;
import com.nhnacademy.front.shop.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
    @Value("${gateway-base-url}")
    private String gatewayUrl;

    private final UserService userService;

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, Model model) {
        setLoginError(request, model);
        model.addAttribute("payco", gatewayUrl + "/auth/oauth2/authorization/payco");
        return "auth/login";
    }

    @GetMapping("/admin/login")
    public String getAdminLogin(HttpServletRequest request, Model model) {
        setLoginError(request, model);
        return "admin/auth/login";
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute OAuth2UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        return "auth/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute RegisterRequest request) {
        userService.registerUser(request);
        return "redirect:/";
    }

    @GetMapping("/reactivate")
    public String getReactivate() {
        return "auth/reactivate";
    }

    @PostMapping("/reactivate")
    public String postReactivate() {
        return "redirect:/login";
    }

    private void setLoginError(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            FlashMessageDto flashError = (FlashMessageDto) session.getAttribute("flashError");
            if (flashError != null) {
                model.addAttribute("flashError", flashError);
                session.removeAttribute("flashError");
            }
        }
    }
}
