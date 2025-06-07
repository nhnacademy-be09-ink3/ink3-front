package com.nhnacademy.front.shop.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShipmentCreateRequest {
    @NotNull
    private LocalDate preferredDeliveryDate;
    @NotBlank
    @Length(max = 50)
    private String recipientName;
    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String recipientPhone;
    @NotNull
    private Integer postalCode;
    @NotBlank
    @Length(max = 100)
    private String defaultAddress;
    @Length(max = 100)
    @NotBlank
    private String detailAddress;
    @Length(max = 100)
    private String extraAddress;
    @NotNull
    private Integer shippingFee;
    @Length(max = 20)
    private String shippingCode;
}
