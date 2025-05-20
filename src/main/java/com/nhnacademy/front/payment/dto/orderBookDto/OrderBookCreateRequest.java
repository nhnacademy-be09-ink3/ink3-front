package com.nhnacademy.front.payment.dto.orderBookDto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderBookCreateRequest {
    @NotNull
    private long bookId;
    @Nullable
    private long packagingId;
    @Nullable
    private long couponStoreId;
    @NotNull
    private int price;
    @NotNull
    private int quantity;
}
