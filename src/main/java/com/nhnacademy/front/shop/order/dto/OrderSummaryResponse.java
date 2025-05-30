package com.nhnacademy.front.shop.order.dto;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderSummaryResponse {
    private OrderResponse order;
    private BookResponse book; // 대표 도서
    private int totalBooks;
}
