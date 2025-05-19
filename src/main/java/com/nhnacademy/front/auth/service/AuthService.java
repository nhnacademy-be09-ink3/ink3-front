package com.nhnacademy.front.auth.service;

import com.nhnacademy.front.auth.client.AuthClient;
import com.nhnacademy.front.auth.client.dto.LoginRequest;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.UserRole;
import com.nhnacademy.front.auth.dto.ClientLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthClient authClient;

    public LoginResponse login(ClientLoginRequest clientLoginRequest, UserRole role) {
        LoginRequest authRequest = new LoginRequest(
                clientLoginRequest.id(),
                clientLoginRequest.password(),
                role
        );
        return authClient.login(authRequest).data();
    }
}
