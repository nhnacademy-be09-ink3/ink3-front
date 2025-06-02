package com.nhnacademy.front.shop.order.dto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderWithDetailsResponse  {
    private Long id;
    private String orderUUID;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private String ordererName;
    private String ordererPhone;
    private Integer paymentAmount;
    private String representativeBookName;
    private String representativeThumbnailUrl;
    private Integer bookTypeCount;
}
