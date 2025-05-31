package com.nhnacademy.front.common.security;

import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.TokenValidateClient;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(
            HttpSecurity http,
            AuthService authService,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtLogoutHandler jwtLogoutHandler,
            @Qualifier("adminLoginSuccessHandler") LoginSuccessHandler loginSuccessHandler,
            @Qualifier("adminLoginFailureHandler") LoginFailureHandler loginFailureHandler
    ) throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(
                "/admin/login",
                authService,
                UserType.ADMIN,
                http.getSharedObject(AuthenticationManager.class)
        );
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        http.securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/logout").permitAll()
                        .anyRequest().hasRole("ADMIN")
                ).addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                ).addFilterBefore(
                        loginAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                ).logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessUrl("/admin")
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect(request.getContextPath() + "/admin/login")
                        )
                        .accessDeniedPage("/error/403")
                ).formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtLogoutHandler jwtLogoutHandler,
            @Qualifier("userLoginSuccessHandler") LoginSuccessHandler loginSuccessHandler,
            @Qualifier("userLoginFailureHandler") LoginFailureHandler loginFailureHandler
    ) throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(
                "/login",
                authService,
                UserType.USER,
                http.getSharedObject(AuthenticationManager.class)
        );
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/me/**").hasRole("USER")
                        .anyRequest().permitAll()
                ).addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                ).addFilterBefore(
                        loginAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessUrl("/")
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect(request.getContextPath() + "/login")
                        )
                        .accessDeniedPage("/login")
                ).formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthClient authClient,
            TokenValidateClient tokenValidateClient
    ) {
        return new JwtAuthenticationFilter(authClient, tokenValidateClient);
    }

    @Bean
    public JwtLogoutHandler jwtLogoutHandler(AuthService authService) {
        return new JwtLogoutHandler(authService);
    }

    @Bean
    public LoginSuccessHandler adminLoginSuccessHandler() {
        return new LoginSuccessHandler("/admin");
    }

    @Bean
    public LoginFailureHandler adminLoginFailureHandler() {
        return new LoginFailureHandler("/admin/login?error");
    }

    @Bean
    public LoginSuccessHandler userLoginSuccessHandler() {
        return new LoginSuccessHandler("/");
    }

    @Bean
    public LoginFailureHandler userLoginFailureHandler() {
        return new LoginFailureHandler("/login?error");
    }
}
