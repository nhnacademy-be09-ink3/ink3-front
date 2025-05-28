package com.nhnacademy.front.shop.point.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.point.client.PointClient;
import com.nhnacademy.front.shop.point.client.dto.PointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointClient pointClient;

    public PageResponse<PointHistoryResponse> getPointHistories(int page, int size, String sort) {
        return pointClient.getPointHistories(page, size, sort).data();
    }
}
