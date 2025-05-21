package com.nhnacademy.front.common.config;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("auth")
public class AuthProperties {
    private final List<String> protectedPaths;

    public AuthProperties(List<String> protectedPaths) {
        this.protectedPaths = Objects.isNull(protectedPaths) ? List.of() : protectedPaths;
    }
}
