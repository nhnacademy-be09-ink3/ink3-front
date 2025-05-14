package com.nhnacademy.front.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AccessTokenInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        if (Objects.isNull(modelAndView)) {
            return;
        }

        String accessToken = getAccessToken(request);

        if (Objects.isNull(accessToken)) {
            modelAndView.addObject("isLoggedIn", false);
            return;
        }

        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            modelAndView.addObject("isLoggedIn", true);
        } catch (Exception e) {
            modelAndView.addObject("isLoggedIn", false);
        }
    }

    private String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst().map(Cookie::getValue).orElse(null);
    }
}
