package com.nhnacademy.front.cart.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.cart.dto.CartRequest;
import com.nhnacademy.front.cart.dto.CartResponse;
import com.nhnacademy.front.cart.dto.CartUpdateRequest;

@FeignClient(name = "cartClient", url = "${feign.url.shop}")
public interface CartClient {
    @PostMapping("/carts")
    ResponseEntity<CartResponse> addCart(@RequestBody CartRequest request);

    @PutMapping("/carts/{cartId}")
    ResponseEntity<CartResponse> updateQuantity(@PathVariable Long cartId, @RequestBody CartUpdateRequest request);

    @GetMapping("/carts/user/{userId}")
    ResponseEntity<List<CartResponse>> getCarts(@PathVariable Long userId);

    @DeleteMapping("/carts/user/{userId}")
    ResponseEntity<Void> deleteCarts(@PathVariable Long userId);

    @DeleteMapping("/carts/{cartId}")
    ResponseEntity<Void> deleteCart(@PathVariable Long cartId);
}
