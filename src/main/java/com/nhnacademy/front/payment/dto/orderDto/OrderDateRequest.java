package com.nhnacademy.front.payment.dto.orderDto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderDateRequest {
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
}
