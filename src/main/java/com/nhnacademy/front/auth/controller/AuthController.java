package com.nhnacademy.front.auth.controller;

import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.dto.LoginRequest;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.LogoutRequest;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.auth.dto.ClientLoginRequest;
import com.nhnacademy.front.common.dto.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthClient authClient;

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute ClientLoginRequest clientLoginRequest, HttpServletResponse response) {
        log.info("Login request: {}", clientLoginRequest);

        LoginRequest authRequest = new LoginRequest(
                clientLoginRequest.id(),
                clientLoginRequest.password(),
                UserType.USER
        );

        CommonResponse<LoginResponse> authResponse = authClient.login(authRequest);
        log.info("Login response: {}", authResponse);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.data().refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", authResponse.data().accessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(30))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        return "redirect:/";
    }

    @GetMapping("/admin/login")
    public String getAdminLogin() {
        return "auth/admin-login";
    }

    @PostMapping("/admin/login")
    public String postAdminLogin(@ModelAttribute ClientLoginRequest clientLoginRequest, HttpServletResponse response) {
        log.info("Login request: {}", clientLoginRequest);

        LoginRequest authRequest = new LoginRequest(
                clientLoginRequest.id(),
                clientLoginRequest.password(),
                UserType.ADMIN
        );

        CommonResponse<LoginResponse> authResponse = authClient.login(authRequest);
        log.info("Login response: {}", authResponse);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.data().refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", authResponse.data().accessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(30))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String postLogout(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            HttpServletResponse response
    ) {
        if (Objects.nonNull(accessToken)) {
            authClient.logout(new LogoutRequest(accessToken));
        }
        ResponseCookie expiredAccessToken = ResponseCookie.from("accessToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
        ResponseCookie expiredRefreshToken = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, expiredAccessToken.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, expiredRefreshToken.toString());
        return "redirect:/";
    }
}
