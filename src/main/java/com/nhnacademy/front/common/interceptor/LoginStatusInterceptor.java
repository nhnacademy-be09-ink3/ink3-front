package com.nhnacademy.front.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginStatusInterceptor implements HandlerInterceptor {
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

        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        if (Objects.isNull(refreshToken)) {
            modelAndView.addObject("isLoggedIn", false);
            return;
        }

        try {
            DecodedJWT jwt = JWT.decode(refreshToken);
            modelAndView.addObject("isLoggedIn", true);
        } catch (Exception e) {
            modelAndView.addObject("isLoggedIn", false);
        }
    }
}
