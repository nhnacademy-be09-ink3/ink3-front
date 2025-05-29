package com.nhnacademy.front.auth.controller;

import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.auth.dto.ClientLoginRequest;
import com.nhnacademy.front.auth.service.AuthService;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthClient authClient;
    private final AuthService authService;

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute ClientLoginRequest clientLoginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(clientLoginRequest, UserType.USER);
        CookieUtil.setTokenCookies(response, loginResponse.accessToken(), loginResponse.refreshToken());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            HttpServletResponse response
    ) {
        authService.logout(accessToken, response);
        return "redirect:/";
    }

    @GetMapping("/admin/login")
    public String getAdminLogin() {
        return "admin/auth/login";
    }

    @PostMapping("/admin/login")
    public String postAdminLogin(@ModelAttribute ClientLoginRequest clientLoginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(clientLoginRequest, UserType.ADMIN);
        CookieUtil.setTokenCookies(response, loginResponse.accessToken(), loginResponse.refreshToken());
        return "redirect:/admin";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            HttpServletResponse response
    ) {
        authService.logout(accessToken, response);
        return "redirect:/admin";
    }
}
