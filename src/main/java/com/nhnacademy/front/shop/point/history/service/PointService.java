package com.nhnacademy.front.shop.point.history.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.point.history.client.PointHistoryClient;
import com.nhnacademy.front.shop.point.history.client.dto.PointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointHistoryClient pointHistoryClient;

    public PageResponse<PointHistoryResponse> getPointHistories(int page, int size, String sort) {
        return pointHistoryClient.getPointHistories(page, size, sort).data();
    }
}
