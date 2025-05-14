package com.nhnacademy.front.common.resolver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.auth.client.dto.UserType;
import com.nhnacademy.front.common.dto.LoginUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> {
                    DecodedJWT jwt = JWT.decode(cookie.getValue());
                    Long id = jwt.getClaim("id").asLong();
                    String role = jwt.getClaim("role").asString();
                    if (Objects.isNull(id) || Objects.isNull(role)) {
                        return null;
                    }
                    return new LoginUser(id, UserType.valueOf(role.toUpperCase()));
                })
                .orElse(null);
    }
}
