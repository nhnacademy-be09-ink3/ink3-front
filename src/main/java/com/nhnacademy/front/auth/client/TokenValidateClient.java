package com.nhnacademy.front.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("prod")
@FeignClient(name = "tokenValidateClient", url = "${feign.url.gateway}")
public interface TokenValidateClient {
    @GetMapping("/token/validate")
    void validateToken();
}
