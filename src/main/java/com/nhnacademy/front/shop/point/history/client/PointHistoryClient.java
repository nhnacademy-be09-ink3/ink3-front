package com.nhnacademy.front.shop.point.history.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.point.history.client.dto.PointHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pointHistoryClient", url = "${feign.url.shop}/users/me/points")
public interface PointHistoryClient {
    @GetMapping
    CommonResponse<PageResponse<PointHistoryResponse>> getPointHistories(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort
    );
}
