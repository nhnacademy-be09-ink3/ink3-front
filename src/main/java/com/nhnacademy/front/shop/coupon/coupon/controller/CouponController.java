package com.nhnacademy.front.shop.coupon.coupon.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.BookResponse;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.CouponClient;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponCreateRequest;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse;
import com.nhnacademy.front.shop.coupon.policy.client.CouponPolicyClient;
import com.nhnacademy.front.shop.coupon.policy.client.dto.PolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자용 쿠폰 등록 폼을 렌더링하고,
 * 제출된 폼 데이터를 백엔드에 전달하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class CouponController {

    private final CouponPolicyClient policyClient;
    private final BookClient bookClient;
    private final CategoryClient categoryClient;
    private final CouponClient couponClient;

    /**
     * 쿠폰 등록 폼 보여주기
     */
    @GetMapping("/coupon-register")
    public String showCouponRegisterForm(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        // 1) 정책 목록 조회 (page, size → 쿼리 파라미터에서 넘어온 값을 그대로 사용)
        CommonResponse<PageResponse<PolicyResponse>> policyResp =
                policyClient.getAllPolicies(size, page);
        PageResponse<PolicyResponse> policies = policyResp.data();
        model.addAttribute("policies", policies);

        // 2) 나머지 목록 조회도 동일하게
        CommonResponse<PageResponse<BookResponse>> bookResp =
                bookClient.getBooks();
        PageResponse<BookResponse> books = bookResp.data();
        model.addAttribute("books", books);

        CommonResponse<PageResponse<CategoryResponse>> categoryResp =
                categoryClient.getCategories();
        PageResponse<CategoryResponse> categories = categoryResp.data();
        model.addAttribute("categories", categories);

        // 3) 뷰 이름 반환
        return "redirect:/admin/coupon/coupon-register";
    }

    /**
     * 쿠폰 등록 폼 제출 처리
     */
    @PostMapping("/coupon-register")
    public String registerCoupon(
            @ModelAttribute CouponCreateRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        // 백엔드로 생성 요청 전송
        CommonResponse<CouponResponse> resp = couponClient.createCoupon(request);

        if (resp.status() == 201) {
            // 등록 성공 시, 상세 페이지나 목록으로 리다이렉트
            return "redirect:/admin/coupon/list";
        } else {
            // 오류 발생 시 메시지를 모델에 담아 다시 폼으로 돌아가기
            model.addAttribute("errorMessage", resp.message());
            // 폼에 필요한 리스트들을 다시 조회
            model.addAttribute("policies", policyClient.getAllPolicies(size, page).data());
            model.addAttribute("books", bookClient.getBooks().data());
            model.addAttribute("categories", categoryClient.getCategories().data());
            return "admin/coupon/coupon-register";
        }
    }
}
