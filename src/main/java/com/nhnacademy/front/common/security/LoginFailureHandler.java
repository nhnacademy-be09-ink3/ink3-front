package com.nhnacademy.front.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final String redirectUrl;

    public LoginFailureHandler(String redirectUrl) {
        Assert.isTrue(
                UrlUtils.isValidRedirectUrl(redirectUrl),
                () -> "'" + redirectUrl + "' is not a valid forward URL"
        );
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        response.sendRedirect(request.getContextPath() + this.redirectUrl);
    }
}
