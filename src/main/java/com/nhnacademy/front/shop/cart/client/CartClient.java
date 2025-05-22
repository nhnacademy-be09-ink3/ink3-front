package com.nhnacademy.front.shop.cart.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.cart.dto.CartRequest;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;

@FeignClient(name = "cartClient", url = "${feign.url.shop}")
public interface CartClient {
    @PostMapping("/me/carts")
    CommonResponse<CartResponse> addCart(@RequestBody MeCartRequest request);

    @PutMapping("/me/carts/{cartId}")
    CommonResponse<CartResponse> updateQuantity(@PathVariable Long cartId, @RequestBody CartUpdateRequest request);

    @GetMapping("/me/carts")
    CommonResponse<List<CartResponse>> getCarts();

    @DeleteMapping("/me/carts")
    void deleteCarts();

    @DeleteMapping("/me/carts/{cartId}")
    void deleteCart(@PathVariable Long cartId);
}
