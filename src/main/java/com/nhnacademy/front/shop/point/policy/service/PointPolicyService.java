package com.nhnacademy.front.shop.point.policy.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.point.policy.client.PointPolicyClient;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyCreateRequest;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyResponse;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyStatisticsResponse;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyUpdateRequest;
import com.nhnacademy.front.shop.point.policy.dto.PointPolicyUpdateFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointPolicyService {
    private final PointPolicyClient pointPolicyClient;

    public PageResponse<PointPolicyResponse> getPointPolicies(int page, int size, String sort) {
        return pointPolicyClient.getPointPolicies(page, size, sort).data();
    }

    public PointPolicyStatisticsResponse getPointPolicyStatistics() {
        return pointPolicyClient.getPointPolicyStatistics().data();
    }

    public PointPolicyResponse createPointPolicy(PointPolicyCreateRequest request) {
        return pointPolicyClient.createPointPolicy(request).data();
    }

    public PointPolicyResponse updatePointPolicy(PointPolicyUpdateFormRequest request) {
        return pointPolicyClient.updatePointPolicy(
                request.id(),
                new PointPolicyUpdateRequest(
                        request.name(),
                        request.joinPoint(),
                        request.reviewPoint(),
                        request.defaultRate()
                )
        ).data();
    }

    public void activatePointPolicy(long pointPolicyId) {
        pointPolicyClient.activatePointPolicy(pointPolicyId);
    }

    public void deletePointPolicy(long pointPolicyId) {
        pointPolicyClient.deletePointPolicy(pointPolicyId);
    }
}
