package com.nhnacademy.front.shop.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ShippingPolicyCreateRequest {
    @NotBlank
    @Length(max = 20)
    private String name;
    @NotBlank
    private Integer threshold;
    @NotBlank
    private Integer fee;
}
