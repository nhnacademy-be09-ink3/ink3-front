package com.nhnacademy.front.shop.user.service;

import com.nhnacademy.front.shop.user.client.UserClient;
import com.nhnacademy.front.shop.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserDetailResponse;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserClient userClient;

    public UserResponse registerUser(RegisterRequest request) {
        return userClient.createUser(new UserCreateRequest(
                request.id(),
                request.password(),
                request.name(),
                request.email(),
                request.phone(),
                request.birthday()
        )).data();
    }

    public UserResponse getCurrentUser() {
        return userClient.getCurrentUser().data();
    }

    public UserDetailResponse getCurrentUserDetail() {
        return userClient.getCurrentUserDetail().data();
    }
}
