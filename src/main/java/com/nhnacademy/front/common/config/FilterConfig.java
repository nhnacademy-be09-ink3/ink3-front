package com.nhnacademy.front.common.config;

import com.nhnacademy.front.common.filter.AuthRequiredFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthRequiredFilter> tokenRefreshFilter(
            AuthRequiredFilter authRequiredFilter,
            AuthProperties authProperties
    ) {
        FilterRegistrationBean<AuthRequiredFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authRequiredFilter);
        registration.setUrlPatterns(authProperties.getProtectedPaths());
        registration.setOrder(1);
        return registration;
    }
}
