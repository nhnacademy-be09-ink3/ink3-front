package com.nhnacademy.front.shop.order.service;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;
    public PageResponse<OrderResponse> getOrders(Integer page, Integer size) {
        CommonResponse<PageResponse<OrderResponse>> orderListByUser = orderClient.getOrderListByUser(page, size);
        return orderListByUser.data();
    }


}
