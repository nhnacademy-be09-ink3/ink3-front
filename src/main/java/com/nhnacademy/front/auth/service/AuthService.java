package com.nhnacademy.front.auth.service;

import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.dto.LoginRequest;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.LogoutRequest;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthClient authClient;

    public LoginResponse login(LoginRequest request) {
        return authClient.login(request).data();
    }

    public void logout(String accessToken, HttpServletResponse response) {
        if (Objects.nonNull(accessToken)) {
            authClient.logout(new LogoutRequest(accessToken));
        }
        CookieUtil.expireTokenCookies(response);
    }
}
