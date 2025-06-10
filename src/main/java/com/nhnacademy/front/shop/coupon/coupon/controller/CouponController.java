package com.nhnacademy.front.shop.coupon.coupon.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.coupon.coupon.client.CouponClient;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponCreateRequest;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse.BookInfo;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse.CategoryInfo;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponUpdateRequest;
import com.nhnacademy.front.shop.coupon.policy.client.CouponPolicyClient;
import com.nhnacademy.front.shop.coupon.store.client.CouponStore;
import com.nhnacademy.front.util.PageUtil;
import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class CouponController {

    private final CouponPolicyClient policyClient;
    private final BookClient bookClient;
    private final CategoryClient categoryClient;
    private final CouponClient couponClient;
    private final CouponStore couponStore;

    // (1) 등록 폼
    @GetMapping("/coupon-register")
    public String showRegisterForm(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        model.addAttribute("policies", policyClient.getAllPolicies(size, page).data());
        model.addAttribute("books",    bookClient.getBooks(0, 10).data());
        model.addAttribute("categories", categoryClient.getCategories().data());
        return "admin/coupon/coupon-register";
    }

    // (2) 등록 처리
    @PostMapping("/coupon-register")
    public String createCoupon(
            @ModelAttribute CouponCreateRequest req
    ) {
        couponClient.createCoupon(req);
        return "redirect:/admin/coupon/coupon-list";
    }

    // (3) 목록 조회 + 단일 조회
    @GetMapping("/coupon-list")
    public String listOrView(
            @RequestParam(required = false) Long couponId,
            @RequestParam(name="page", defaultValue="0") int page,
            Model model
    ) {
        if (couponId != null) {
            CommonResponse<CouponResponse> resp = couponClient.getById(couponId);
            model.addAttribute("singleCoupon", resp.data());
        } else {
            CommonResponse<PageResponse<CouponResponse>> resp =
                    couponClient.getAll(10, page);
            PageResponse<CouponResponse> paged = resp.data();

            PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                    paged.page(), paged.totalPages(), 5);
            model.addAttribute("coupons", paged);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("pageNum", page);
        }
        return "admin/coupon/coupons";
    }

    @PutMapping("/coupon-list/{id}")
    public String updateCoupon(
            @PathVariable Long id,
            @ModelAttribute("couponUpdateRequest") CouponUpdateRequest req
    ) {
        couponClient.updateCoupon(id, req);
        return "redirect:/admin/coupon/coupon-list";
    }

    @GetMapping("/coupon-list/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        // 1) 기존 쿠폰 조회
        CouponResponse cr = couponClient.getById(id).data();

        // 2) CouponUpdateRequest 로 초기값 세팅
        List<Long> selectedBookIds = cr.books().stream()
                .map(BookInfo::id)
                .toList();
        List<Long> selectedCategoryIds = cr.categories().stream()
                .map(CategoryInfo::id)
                .toList();

        CouponUpdateRequest updateReq = new CouponUpdateRequest(
                cr.policyId(),
                cr.name(),
                cr.issuableFrom(),
                cr.expiresAt(),
                selectedBookIds,
                selectedCategoryIds
        );

        // 3) 모델에 폼 바인딩 객체와 셀렉트 옵션들 추가
        model.addAttribute("couponUpdateRequest", updateReq);
        model.addAttribute("couponId", id);
        model.addAttribute("policies", policyClient.getAllPolicies(10, 0).data());
        model.addAttribute("books",    bookClient.getBooks(0, 10).data());
        model.addAttribute("categories", categoryClient.getCategories().data());

        return "admin/coupon/coupon-update";
    }

    @DeleteMapping("/coupon-list/{id}")
    public String deleteCoupon(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            couponClient.deleteById(id);
        } catch (FeignException e) {
            if (e.status() == 409) {
                String msg = extractMessageFromFeignBody(e.contentUTF8());
                redirectAttributes.addFlashAttribute("errorMessage", msg);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 알 수 없는 오류가 발생했습니다.");
            }
        }
        return "redirect:/admin/coupon/coupon-list";
    }

    private String extractMessageFromFeignBody(String body) {
        try {
            var tree = new com.fasterxml.jackson.databind.ObjectMapper().readTree(body);
            return tree.path("message").asText();
        } catch (Exception ex) {
            return "삭제 중 오류가 발생했습니다.";
        }
    }
}
