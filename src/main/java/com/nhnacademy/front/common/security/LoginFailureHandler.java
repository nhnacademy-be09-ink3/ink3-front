package com.nhnacademy.front.common.security;

import com.nhnacademy.front.common.dto.FlashMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
        HttpSession session = request.getSession();
        switch (exception) {
            case BadCredentialsException e -> session.setAttribute(
                    "flashError",
                    new FlashMessageDto(404, "아이디 또는 비밀번호가 올바르지 않습니다.")
            );
            case LockedException e -> session.setAttribute(
                    "flashError",
                    new FlashMessageDto(423, "휴면 계정입니다.")
            );
            case DisabledException e -> session.setAttribute(
                    "flashError",
                    new FlashMessageDto(410, "탈퇴한 계정입니다.")
            );
            case null, default -> session.setAttribute(
                    "flashError",
                    new FlashMessageDto(500, "인증 서버에 오류가 발생했습니다.")
            );
        }
        response.sendRedirect(request.getContextPath() + this.redirectUrl);
    }
}
