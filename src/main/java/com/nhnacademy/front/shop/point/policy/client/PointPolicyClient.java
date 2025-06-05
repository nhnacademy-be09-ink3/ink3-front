package com.nhnacademy.front.shop.point.policy.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyCreateRequest;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyResponse;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyStatisticsResponse;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pointPolicyClient", url = "${feign.url.shop}/point-policies")
public interface PointPolicyClient {
    @GetMapping
    CommonResponse<PageResponse<PointPolicyResponse>> getPointPolicies(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort
    );

    @GetMapping("/statistics")
    CommonResponse<PointPolicyStatisticsResponse> getPointPolicyStatistics();

    @PostMapping
    CommonResponse<PointPolicyResponse> createPointPolicy(@RequestBody PointPolicyCreateRequest request);

    @PutMapping("/{pointPolicyId}")
    CommonResponse<PointPolicyResponse> updatePointPolicy(
            @PathVariable long pointPolicyId,
            @RequestBody PointPolicyUpdateRequest request
    );

    @PatchMapping("/{pointPolicyId}")
    void activatePointPolicy(@PathVariable long pointPolicyId);

    @DeleteMapping("/{pointPolicyId}")
    void deletePointPolicy(@PathVariable long pointPolicyId);
}
