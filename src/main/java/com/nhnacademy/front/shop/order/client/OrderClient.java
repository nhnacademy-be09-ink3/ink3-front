package com.nhnacademy.front.shop.order.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.예시DTO.AddressResponse;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orderClient", url = "${feign.url.shop}")
public interface OrderClient {
    @GetMapping("/users/{userId}/addresses")
    CommonResponse<PageResponse<AddressResponse>> getUserAddresses(@PathVariable long userId);

    @GetMapping("/users/{userId}")
    CommonResponse<UserResponse> getUser(@PathVariable long userId);
}
