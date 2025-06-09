package com.nhnacademy.front.shop.user.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.user.client.UserClient;
import com.nhnacademy.front.shop.user.client.dto.SocialUserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserDetailResponse;
import com.nhnacademy.front.shop.user.client.dto.UserListItemDto;
import com.nhnacademy.front.shop.user.client.dto.UserPasswordUpdateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.client.dto.UserStatisticsResponse;
import com.nhnacademy.front.shop.user.client.dto.UserUpdateRequest;
import com.nhnacademy.front.shop.user.dto.RegisterRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserClient userClient;

    public UserResponse registerUser(RegisterRequest request) {
        if (Objects.isNull(request.provider()) || request.provider().isEmpty()) {
            return userClient.createUser(new UserCreateRequest(
                    request.id(),
                    request.password(),
                    request.name(),
                    request.email(),
                    request.phone(),
                    request.birthday()
            )).data();
        } else {
            return userClient.createSocialUser(new SocialUserCreateRequest(
                    request.id(),
                    request.password(),
                    request.name(),
                    request.email(),
                    request.phone(),
                    request.birthday(),
                    request.provider(),
                    request.providerId()
            )).data();
        }
    }

    public UserResponse getCurrentUser() {
        return userClient.getCurrentUser().data();
    }

    public PageResponse<UserListItemDto> getUsersForManagement(String keyword, int page, int size) {
        return userClient.getUsers(keyword, page, size).data();
    }

    public UserDetailResponse getCurrentUserDetail() {
        return userClient.getCurrentUserDetail().data();
    }

    public UserResponse updateCurrentUser(UserUpdateRequest request) {
        return userClient.updateCurrentUser(request).data();
    }

    public void updateCurrentUserPassword(UserPasswordUpdateRequest request) {
        userClient.updateCurrentUserPassword(request);
    }

    public void withdrawCurrentUser() {
        userClient.withdrawCurrentUser();
    }

    public UserStatisticsResponse getUserStatistics() {
        return userClient.getUserStatistics().data();
    }
}
