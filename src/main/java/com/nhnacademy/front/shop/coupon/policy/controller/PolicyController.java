package com.nhnacademy.front.shop.coupon.policy.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.coupon.policy.client.CouponPolicyClient;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyResponse;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyUpdateRequest;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class PolicyController {
    private final CouponPolicyClient couponPolicyClient;

    // (1) 목록 + 단일 조회
    @GetMapping("/policies")
    public String getPolicies(
            @RequestParam(required = false) Long policyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            Model model
    ) {
        if (policyId != null) {
            // 단일 조회
            CommonResponse<PolicyResponse> resp = couponPolicyClient.getPolicyById(policyId);
            model.addAttribute("singlePolicy", resp.data());
        } else {
            // 목록 조회 (페이징)
            CommonResponse<PageResponse<PolicyResponse>> resp = couponPolicyClient.getAllPolicies(10, page);
            PageResponse<PolicyResponse> paged = resp.data();
            PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                    paged.page(), paged.totalPages(), 5
            );
            model.addAttribute("policies", paged);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("pageNum", page);
        }
        return "admin/coupon/policies";
    }

    // (2) “수정 폼” 보여주기 (GET)
    @GetMapping("/policies/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        PolicyResponse policy = couponPolicyClient.getPolicyById(id).data();
        model.addAttribute("policy", policy);
        return "admin/coupon/policy-update";
    }

    // (3) 실제 업데이트(PUT) 처리
    @PutMapping("/policies/{id}")
    public String updatePolicy(
            @PathVariable Long id,
            @ModelAttribute PolicyUpdateRequest req
    ) {
        couponPolicyClient.updatePolicy(id, req);
        return "redirect:/admin/policies";
    }

    // (4) 삭제
    @DeleteMapping("/policies/{id}")
    public String deletePolicy(@PathVariable Long id) {
        couponPolicyClient.deletePolicy(id);
        return "redirect:/admin/policies";
    }
}
