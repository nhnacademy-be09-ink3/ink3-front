package com.nhnacademy.front.shop.order.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderFormCreateRequest;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.예시DTO.AddressResponse;
import com.nhnacademy.front.shop.order.예시DTO.CartResponse;
import com.nhnacademy.front.shop.order.예시DTO.CouponStoreResponse;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderClient", url = "${feign.url.shop}")
public interface OrderClient {
    @PostMapping("/orders")
    CommonResponse<OrderResponse> createOrder(@RequestBody OrderFormCreateRequest request);

    @GetMapping("/orders/me")
    CommonResponse<PageResponse<OrderResponse>> getOrderListByUser(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/packagings/activate")
    CommonResponse<PageResponse<PackagingResponse>> getPackagings(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );


    //TODO : 외부 client로 수정
    @GetMapping("/users/{userId}/addresses")
    CommonResponse<PageResponse<AddressResponse>> getUserAddresses(
            @PathVariable Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);


    @GetMapping("/stores/users/{userId}/unused")
    CommonResponse<List<CouponStoreResponse>> getUnusedCoupons(@PathVariable("userId") Long userId);

    @GetMapping("/users/{userId}")
    CommonResponse<UserResponse> getUser(@PathVariable Long userId);

    @GetMapping("/carts/users/{userId}")
    CommonResponse<List<CartResponse>> getCartsWithCoupons(@PathVariable Long userId);
}
