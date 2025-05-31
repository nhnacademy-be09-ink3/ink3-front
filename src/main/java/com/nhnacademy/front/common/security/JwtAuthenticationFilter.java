package com.nhnacademy.front.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.TokenValidateClient;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.ReissueRequest;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthClient authClient;
    private final TokenValidateClient tokenValidateClient;

    private static final List<String> WHITELIST = List.of(
            "/css/", "/script/", "/img/", "/favicon.ico", "/login", "/admin/login"
    );

    private boolean isWhitelisted(String path) {
        return WHITELIST.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (isWhitelisted(request.getRequestURI().substring(request.getContextPath().length()))) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = CookieUtil.getCookieValue(request, "accessToken");
            String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

            if (Objects.isNull(accessToken) || isExpired(accessToken)) {
                accessToken = handleReissue(request, response, refreshToken);
            } else {
                tokenValidateClient.validateToken();
            }

            DecodedJWT decodedAccessToken = JWT.decode(accessToken);
            String username = decodedAccessToken.getSubject();
            UserType userType = UserType.valueOf(
                    decodedAccessToken.getClaim("userType").asString().toUpperCase()
            );

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.warn("Unauthorized: {}", e.getMessage());
            CookieUtil.expireTokenCookies(response);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private boolean isExpired(String jwtToken) {
        final long BUFFER_TIME_MILLIS = 30 * 1000L;

        try {
            DecodedJWT jwt = JWT.decode(jwtToken);
            Date expiresAt = jwt.getExpiresAt();
            long now = System.currentTimeMillis();
            return expiresAt.getTime() - now <= BUFFER_TIME_MILLIS;
        } catch (Exception e) {
            return true;
        }
    }

    private String handleReissue(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        if (Objects.nonNull(refreshToken) && !isExpired(refreshToken)) {
            DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);
            long id = decodeRefreshToken.getClaim("id").asLong();
            UserType userType = UserType.valueOf(
                    decodeRefreshToken.getClaim("userType").asString().toUpperCase()
            );

            LoginResponse reissueRes = authClient.reissue(new ReissueRequest(
                    id,
                    userType,
                    refreshToken
            )).data();

            CookieUtil.setTokenCookies(response, reissueRes.accessToken(), reissueRes.refreshToken());
            request.setAttribute("accessToken", reissueRes.accessToken().token());
            return reissueRes.accessToken().token();
        }
        throw new BadCredentialsException("Refresh token is missing or expired.");
    }
}
