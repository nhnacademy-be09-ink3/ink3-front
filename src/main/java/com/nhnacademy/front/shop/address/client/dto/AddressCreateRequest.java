package com.nhnacademy.front.shop.address.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{5}", message = "Postal code must be exactly 5 digits.")
        String postalCode,

        @NotBlank
        String defaultAddress,

        @NotBlank
        String detailAddress,

        String extraAddress
) {
}
