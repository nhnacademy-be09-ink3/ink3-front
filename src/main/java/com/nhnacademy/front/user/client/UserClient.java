package com.nhnacademy.front.user.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.user.client.dto.UserResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "shop-service")
public interface UserClient {
    @GetMapping("/users")
    CommonResponse<Map<String, Boolean>> checkUserIdentifierAvailability(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String email
    );

    @PostMapping("/users")
    CommonResponse<UserResponse> createUser(@RequestBody UserCreateRequest request);
}
