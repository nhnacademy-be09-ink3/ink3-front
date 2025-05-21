package com.nhnacademy.front.shop.order.예시DTO;

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
