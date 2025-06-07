package com.nhnacademy.front.shop.coupon.policy.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyCreateRequest;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyResponse;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * FeignClient 인터페이스: API 서버의 /policies 경로와 똑같이 매핑되어야 한다.
 */
@FeignClient(name = "policyClient", url = "${feign.url.shop}/policies")
public interface CouponPolicyClient {

    // 1) 생성 → POST /policies
    @PostMapping
    CommonResponse<PolicyResponse> createPolicy(@Valid @RequestBody PolicyCreateRequest request);

    // 2) 전체 조회(페이징) → GET /policies?size=…&page=…
    @GetMapping
    CommonResponse<PageResponse<PolicyResponse>> getAllPolicies(
            @RequestParam int size,
            @RequestParam int page
    );

    // 3) ID 조회 → GET /policies/{policyId}
    @GetMapping("/{policyId}")
    CommonResponse<PolicyResponse> getPolicyById(@PathVariable("policyId") Long policyId);

    // 4) 수정 → PUT /policies/{policyId}
    @PutMapping("/{policyId}")
    CommonResponse<PolicyResponse> updatePolicy(
            @PathVariable("policyId") Long policyId,
            @Valid @RequestBody PolicyUpdateRequest request
    );

    // 5) 삭제 → DELETE /policies/{policyId}
    @DeleteMapping("/{policyId}")
    CommonResponse<PolicyResponse> deletePolicy(@PathVariable("policyId") Long policyId);

    // (필요시) 이름으로 삭제 → DELETE /policies/by-name/{name}
    @DeleteMapping("/by-name/{name}")
    CommonResponse<PolicyResponse> deleteByName(@PathVariable("name") String name);
}
