package com.nhnacademy.front.user.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.user.client.UserClient;
import com.nhnacademy.front.user.client.dto.UserCreateRequest;
import com.nhnacademy.front.user.client.dto.UserResponse;
import com.nhnacademy.front.user.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserClient userClient;

    @GetMapping("/register")
    public String getRegister() {
        return "user/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute RegisterRequest request) {
        log.info("Register request: {}", request);
        CommonResponse<UserResponse> response = userClient.createUser(new UserCreateRequest(
                request.id(),
                request.password(),
                request.name(),
                request.email(),
                request.phone(),
                request.birthday()
        ));
        log.info("Register response: {}", response);
        return "redirect:/";
    }

    @GetMapping("/me")
    public String getMe() {
        return "user/me";
    }
}
