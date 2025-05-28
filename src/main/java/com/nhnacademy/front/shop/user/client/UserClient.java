package com.nhnacademy.front.shop.user.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserDetailResponse;
import com.nhnacademy.front.shop.user.client.dto.UserPasswordUpdateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.client.dto.UserUpdateRequest;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${feign.url.shop}/users")
public interface UserClient {
    @GetMapping
    CommonResponse<Map<String, Boolean>> checkUserIdentifierAvailability(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String email
    );

    @GetMapping("/me")
    CommonResponse<UserResponse> getCurrentUser();

    @GetMapping("/me/detail")
    CommonResponse<UserDetailResponse> getCurrentUserDetail();

    @PostMapping
    CommonResponse<UserResponse> createUser(@RequestBody UserCreateRequest request);

    @PutMapping("/me")
    CommonResponse<UserResponse> updateCurrentUser(@RequestBody UserUpdateRequest request);

    @PatchMapping("/me/password")
    CommonResponse<Void> updateCurrentUserPassword(@RequestBody UserPasswordUpdateRequest request);

    @PatchMapping("/me/withdraw")
    CommonResponse<Void> withdrawCurrentUser();
}
