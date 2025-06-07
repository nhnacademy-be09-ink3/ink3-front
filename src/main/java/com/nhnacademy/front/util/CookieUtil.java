package com.nhnacademy.front.util;

import com.nhnacademy.front.auth.client.dto.JwtToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class CookieUtil {
    private CookieUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies)) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst().map(Cookie::getValue).orElse(null);
    }

    public static void setTokenCookies(
            HttpServletResponse response,
            JwtToken accessToken,
            JwtToken refreshToken,
            boolean rememberMe
    ) {
        long now = System.currentTimeMillis();

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken.token())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(rememberMe ? Duration.ofMillis(accessToken.expiresAt() - now) : Duration.ofSeconds(-1))
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken.token())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(rememberMe ? Duration.ofMillis(refreshToken.expiresAt() - now) : Duration.ofSeconds(-1))
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    public static void expireTokenCookies(HttpServletResponse response) {
        ResponseCookie expiredAccessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie expiredRefreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, expiredAccessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, expiredRefreshCookie.toString());
    }
}
