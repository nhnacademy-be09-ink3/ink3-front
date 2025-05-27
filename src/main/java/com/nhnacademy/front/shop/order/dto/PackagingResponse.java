package com.nhnacademy.front.shop.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PackagingResponse {
    private Long id;
    private String name;
    private Integer price;
    private Boolean isAvailable;
}
