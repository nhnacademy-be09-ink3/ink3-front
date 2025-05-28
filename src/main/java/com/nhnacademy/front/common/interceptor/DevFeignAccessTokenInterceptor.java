package com.nhnacademy.front.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.util.CookieUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@ConditionalOnMissingBean(FeignAccessTokenInterceptor.class)
@Component
public class DevFeignAccessTokenInterceptor implements RequestInterceptor {
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
            DecodedJWT decodedAccessToken = JWT.decode(accessToken);
            long id = decodedAccessToken.getClaim("id").asLong();
            UserType userType = UserType.valueOf(
                    decodedAccessToken.getClaim("userType").asString().toUpperCase()
            );
            
            template.header("X-User-Id", String.valueOf(id));
            template.header("X-User-Role", userType.name());
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
