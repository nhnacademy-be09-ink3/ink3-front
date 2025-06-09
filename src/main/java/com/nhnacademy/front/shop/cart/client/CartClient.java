package com.nhnacademy.front.shop.cart.client;

import com.nhnacademy.front.shop.book.dto.BookResponse;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;
import com.nhnacademy.front.shop.cart.dto.CartCouponResponse;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;

@FeignClient(name = "cartClient", url = "${feign.url.shop}")
public interface CartClient {
    @PostMapping("/me/carts")
    CommonResponse<CartResponse> addCart(@RequestBody MeCartRequest request);

    @PostMapping("/me/carts/merge-guest")
    void mergeGuestCart(@RequestBody List<MeCartRequest> guestItems);

    @PutMapping("/me/carts/{cartId}")
    CommonResponse<CartResponse> updateQuantity(@PathVariable Long cartId, @RequestBody CartUpdateRequest request);

    @GetMapping("/me/carts/coupons")
    CommonResponse<List<CartCouponResponse>> getCartsWithCoupon();

    @GetMapping("/me/carts")
    CommonResponse<List<CartResponse>> getCarts();

    @DeleteMapping("/me/carts")
    void deleteCarts();

    @DeleteMapping("/me/carts/{cartId}")
    void deleteCart(@PathVariable Long cartId);

    @GetMapping("/books/{bookId}")
    CommonResponse<BookResponse> getBookById(@PathVariable Long bookId);
}
