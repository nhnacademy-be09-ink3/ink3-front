package com.nhnacademy.front.common.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.ReissueRequest;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.util.CookieUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthRequiredFilter implements Filter {
    private final AuthClient authClient;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = CookieUtil.getCookieValue(httpRequest, "accessToken");

        if (Objects.isNull(accessToken) || isExpired(accessToken)) {
            String refreshToken = CookieUtil.getCookieValue(httpRequest, "refreshToken");

            if (Objects.nonNull(refreshToken) && !isExpired(refreshToken)) {
                try {
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

                    CookieUtil.setTokenCookies(httpResponse, reissueRes.accessToken(), reissueRes.refreshToken());
                    httpRequest.setAttribute("accessToken", reissueRes.accessToken().token());
                } catch (Exception e) {
                    httpResponse.sendRedirect("/login");
                    return;
                }
            } else {
                httpResponse.sendRedirect("/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isExpired(String accessToken) {
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            Date expiresAt = jwt.getExpiresAt();
            return expiresAt.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
