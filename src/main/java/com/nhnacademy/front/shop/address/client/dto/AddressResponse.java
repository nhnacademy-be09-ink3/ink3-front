package com.nhnacademy.front.shop.address.client.dto;

public record AddressResponse(
        Long id,
        String name,
        String postalCode,
        String defaultAddress,
        String detailAddress,
        String extraAddress,
        Boolean isDefault
) {
}
