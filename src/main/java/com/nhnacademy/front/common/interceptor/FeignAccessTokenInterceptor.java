package com.nhnacademy.front.common.interceptor;

import com.nhnacademy.front.util.CookieUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
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

        Object accessTokenAttribute = attributes.getRequest().getAttribute("accessToken");

        String accessToken = Objects.nonNull(accessTokenAttribute)
                ? accessTokenAttribute.toString()
                : CookieUtil.getCookieValue(attributes.getRequest(), "accessToken");

        if (Objects.nonNull(accessToken)) {
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }
    }
}
