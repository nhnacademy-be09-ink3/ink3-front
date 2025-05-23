package com.nhnacademy.front.shop.user.client;

import com.nhnacademy.front.common.config.LoggerLevelConfiguration;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserDetailResponse;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${feign.url.shop}", configuration = LoggerLevelConfiguration.class)
public interface UserClient {
    @GetMapping("/users")
    CommonResponse<Map<String, Boolean>> checkUserIdentifierAvailability(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String email
    );

    @GetMapping("/users/me")
    CommonResponse<UserResponse> getCurrentUser();

    @GetMapping("/users/me/detail")
    CommonResponse<UserDetailResponse> getCurrentUserDetail();

    @PostMapping("/users")
    CommonResponse<UserResponse> createUser(@RequestBody UserCreateRequest request);
}
