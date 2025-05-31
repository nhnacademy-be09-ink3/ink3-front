package com.nhnacademy.front.common.security;

import com.nhnacademy.front.auth.service.AuthService;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
    private final AuthService authService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = CookieUtil.getCookieValue(request, "accessToken");
        authService.logout(accessToken, response);
    }
}
