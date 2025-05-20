package com.nhnacademy.front.payment.dto.orderBookDto;


import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderBookListCreateRequest {
    @NotNull
    private long orderId;
    @NotNull
    private List<OrderBookCreateRequest> orderBooks;
}