package com.nhnacademy.front.shop.address.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressCreateRequest;
import com.nhnacademy.front.shop.address.client.dto.AddressResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "addressClient", url = "${feign.url.shop}/users/me/addresses")
public interface AddressClient {
    @GetMapping("/{addressId}")
    CommonResponse<AddressResponse> getAddress(@PathVariable long addressId);

    @GetMapping
    CommonResponse<PageResponse<AddressResponse>> getAddresses(@RequestParam int page, @RequestParam int size);

    @PostMapping
    CommonResponse<AddressResponse> createAddress(@RequestBody AddressCreateRequest request);

    @PutMapping("/{addressId}")
    CommonResponse<AddressResponse> updateAddress(
            @PathVariable long addressId,
            @RequestBody AddressUpdateRequest request
    );

    @PatchMapping("/{addressId}/default")
    CommonResponse<Void> setDefaultAddress(@PathVariable long addressId);

    @DeleteMapping("/{addressId}")
    CommonResponse<Void> deleteAddress(@PathVariable long addressId);
}
