package com.nhnacademy.front.shop.user.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;

@FeignClient(name = "userClient", url = "${feign.url.shop}")
public interface UserClient {
    @GetMapping("/users")
    CommonResponse<Map<String, Boolean>> checkUserIdentifierAvailability(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String email
    );

    @PostMapping("/users")
    CommonResponse<UserResponse> createUser(@RequestBody UserCreateRequest request);
}
