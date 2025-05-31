package com.nhnacademy.front.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final String redirectUrl;

    public LoginSuccessHandler(String redirectUrl) {
        Assert.isTrue(
                UrlUtils.isValidRedirectUrl(redirectUrl),
                () -> "'" + redirectUrl + "' is not a valid forward URL"
        );
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
