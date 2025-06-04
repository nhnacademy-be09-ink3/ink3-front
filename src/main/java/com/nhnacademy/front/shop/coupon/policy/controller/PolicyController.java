package com.nhnacademy.front.shop.coupon.policy.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.coupon.policy.client.CouponPolicyClient;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyCreateRequest;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyResponse;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PolicyController {

    private final CouponPolicyClient couponPolicyClient;

    @PostMapping("/admin/policy-register")
    public String createPolicy(@ModelAttribute PolicyCreateRequest req) {
        couponPolicyClient.createPolicy(req);
        return "redirect:/admin/policy-register";
    }

    @GetMapping("/admin/policy-register")
    public String policyRegister() {
        return "admin/policy-register";
    }

    @GetMapping("/admin/policies")
    public String getPolicies(
            @RequestParam(required = false, defaultValue = "0") int page,
            Model model
    ) {
        // 1) client를 통해 페이지 크기(size=10), 현재 페이지(page)로 정책 목록 조회
        CommonResponse<PageResponse<PolicyResponse>> response =
                couponPolicyClient.getAllPolicies(10, page);

        // 2) 실제 데이터(PageResponse<PolicyResponse>)를 꺼내 옴
        PageResponse<PolicyResponse> policies = response.data();

        // 3) 페이징 네비게이션용 PageInfo 계산 (PageUtil 참고)
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                policies.page(),   // 현재 0-based 페이지 번호
                policies.totalPages(),
                5                  // 한 번에 보여줄 페이지 번호 블록 크기(예: 1–5, 6–10 등)
        );

        // 4) Model에 필요한 속성 담기
        model.addAttribute("currentPage", "policies");
        model.addAttribute("policies", policies);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/policies";
    }
}
