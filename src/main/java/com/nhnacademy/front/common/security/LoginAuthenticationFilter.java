package com.nhnacademy.front.common.security;

import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.auth.dto.ClientLoginRequest;
import com.nhnacademy.front.auth.service.AuthService;
import com.nhnacademy.front.util.CookieUtil;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    @Getter
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    @Getter
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    @Getter
    @Setter
    private UserType userType;

    private final AuthService authService;

    public LoginAuthenticationFilter(
            String filterProcessesUrl,
            AuthService authService,
            UserType userType,
            AuthenticationManager authenticationManager
    ) {
        super(new AntPathRequestMatcher(filterProcessesUrl, "POST"), authenticationManager);
        this.authService = authService;
        this.userType = userType;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            String username = obtainUsername(request);
            username = (username != null) ? username.trim() : "";
            String password = obtainPassword(request);
            password = (password != null) ? password : "";

            LoginResponse loginResponse = authService.login(new ClientLoginRequest(username, password), userType);

            CookieUtil.setTokenCookies(response, loginResponse.accessToken(), loginResponse.refreshToken());

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()));
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (FeignException e) {
            int statusCode = e.status();
            switch (statusCode) {
                case 401, 404 -> throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.", e);
                case 423 -> throw new LockedException("휴면 계정입니다.", e);
                case 410 -> throw new DisabledException("탈퇴한 계정입니다.", e);
                default -> throw new AuthenticationServiceException("인증서버에 오류가 발생하였습니다.", e);
            }
        } catch (Exception e) {
            throw new AuthenticationServiceException("인증 과정에서 알 수 없는 오류가 발생하였습니다.", e);
        }
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }
}
