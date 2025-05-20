package com.nhnacademy.front.auth.client;

import com.nhnacademy.front.auth.client.dto.LoginRequest;
import com.nhnacademy.front.auth.client.dto.LoginResponse;
import com.nhnacademy.front.auth.client.dto.LogoutRequest;
import com.nhnacademy.front.auth.client.dto.ReissueRequest;
import com.nhnacademy.front.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authClient", url = "${feign.url.auth}")
public interface AuthClient {
    @PostMapping("/login")
    CommonResponse<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestBody LogoutRequest request);

    @PostMapping("/reissue")
    CommonResponse<LoginResponse> reissue(@RequestBody ReissueRequest request);
}
