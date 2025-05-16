package com.nhnacademy.front.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.user.client.dto.CartRequest;
import com.nhnacademy.front.user.client.dto.GuestCartRequest;
import com.nhnacademy.front.user.client.dto.CartResponse;
import com.nhnacademy.front.user.client.dto.CartUpdateRequest;

@FeignClient(name = "shop-service")
public interface CartClient {
    @PostMapping
   ResponseEntity<CartResponse> addCart(@RequestBody CartRequest request);

    @PutMapping("/{cartId}")
    ResponseEntity<CartResponse> updateQuantity(@PathVariable Long cartId, @RequestBody CartUpdateRequest request);

    @GetMapping("/user/{userId}")
    ResponseEntity<List<CartResponse>> getCarts(@PathVariable Long userId);

    // @PostMapping("/guest")
    // ResponseEntity<List<CartResponse>> getGuestCarts(@RequestBody List<GuestCartRequest> requests);

    @DeleteMapping("/user/{userId}")
    ResponseEntity<Void> deleteCarts(@PathVariable Long userId);

    @DeleteMapping("/{cartId}")
    ResponseEntity<Void> deleteCart(@PathVariable Long cartId);
}
