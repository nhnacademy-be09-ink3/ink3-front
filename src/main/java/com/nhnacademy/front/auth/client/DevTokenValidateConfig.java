package com.nhnacademy.front.auth.client;

import com.nhnacademy.front.util.CookieUtil;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@ConditionalOnMissingBean(TokenValidateClient.class)
@Configuration
public class DevTokenValidateConfig {

    @Bean(name = "tokenValidateClient")
    public TokenValidateClient devTokenValidateClient() {
        return () -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();

            String accessToken = CookieUtil.getCookieValue(request, "accessToken");

            if (Objects.isNull(accessToken) || accessToken.isBlank()) {
                log.warn("DevTokenValidateClient: No access token - throwing 401");
                throw new FeignException.Unauthorized("Missing access token", null, null, null);
            }

            log.debug("DevTokenValidateClient: Access token found, bypassing validation");
        };
    }
}
