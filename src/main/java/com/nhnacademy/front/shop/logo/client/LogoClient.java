package com.nhnacademy.front.shop.logo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "locoClient", url = "${feign.url.shop}")
public interface LogoClient {
    @GetMapping("/logo-url")
    String getLogoPresignedUrl();
}
