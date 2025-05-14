package com.nhnacademy.front.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignAccessTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(attributes)) {
            return;
        }

        Cookie[] cookies = attributes.getRequest().getCookies();

        if (Objects.isNull(cookies)) {
            return;
        }

        Arrays.stream(cookies)
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .ifPresent(accessToken -> template.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));
    }
}
