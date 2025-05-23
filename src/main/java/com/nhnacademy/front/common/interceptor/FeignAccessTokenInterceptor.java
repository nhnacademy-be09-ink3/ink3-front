package com.nhnacademy.front.common.interceptor;

import com.nhnacademy.front.util.CookieUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
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
        template.header(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        template.header(HttpHeaders.ACCEPT, "application/json");
        template.header(HttpHeaders.CONTENT_TYPE, "application/json");
        log.info("Feign Request → Method: {}, URL: {}", template.method(), template.url());
        log.info("Headers: {}", template.headers());
        if (template.body() != null) {
            log.info("Body: {}", new String(template.body(), StandardCharsets.UTF_8));
        }
    }
}
