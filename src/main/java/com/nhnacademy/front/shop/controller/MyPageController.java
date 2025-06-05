package com.nhnacademy.front.shop.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.front.auth.client.dto.OAuth2UserInfo;
import com.nhnacademy.front.auth.service.AuthService;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressCreateRequest;
import com.nhnacademy.front.shop.address.client.dto.AddressUpdateRequest;
import com.nhnacademy.front.shop.address.service.AddressService;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.like.client.dto.LikeResponse;
import com.nhnacademy.front.shop.like.service.LikeService;
import com.nhnacademy.front.shop.order.dto.OrderWithDetailsResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.point.client.dto.PointHistoryResponse;
import com.nhnacademy.front.shop.point.service.PointService;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewListResponse;
import com.nhnacademy.front.shop.user.client.dto.UserPasswordUpdateRequest;
import com.nhnacademy.front.shop.user.client.dto.UserUpdateRequest;
import com.nhnacademy.front.shop.user.dto.RegisterRequest;
import com.nhnacademy.front.shop.user.service.UserService;
import com.nhnacademy.front.util.PageUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {
    private final UserService userService;
    private final AuthService authService;
    private final AddressService addressService;
    private final PointService pointService;
    private final LikeService likeService;
    private final OrderService orderService;
    private final BookClient bookClient;
    private final ReviewClient reviewClient;

    @GetMapping("/register")
    public String getRegister(@ModelAttribute OAuth2UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        return "user/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute RegisterRequest request) {
        userService.registerUser(request);
        return "redirect:/";
    }

    @GetMapping("/me")
    public String getMe(Model model) {
        model.addAttribute("currentPage", "me");
        model.addAttribute("user", userService.getCurrentUserDetail());
        return "user/me";
    }

    @PutMapping("/me")
    public String updateMe(@ModelAttribute @Valid UserUpdateRequest request) {
        userService.updateCurrentUser(request);
        return "redirect:/me";
    }

    @PutMapping("/me/password")
    public String updatePassword(
            @ModelAttribute @Valid UserPasswordUpdateRequest request,
            @CookieValue String accessToken,
            HttpServletResponse httpServletResponse
    ) {
        userService.updateCurrentUserPassword(request);
        authService.logout(accessToken, httpServletResponse);
        return "redirect:/";
    }

    @GetMapping("/me/orders")
    public String getMeOrders(@RequestParam(defaultValue = "0") int page,
                              Model model) {
        PageResponse<OrderWithDetailsResponse> orderResponse = orderService.getOrders(page, 7);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                orderResponse.page(), orderResponse.totalPages(), 5);

        model.addAttribute("currentPage", "orders");
        model.addAttribute("user", userService.getCurrentUserDetail());
        model.addAttribute("orderResponseList", orderResponse);
        model.addAttribute("pageInfo", pageInfo);
        return "user/me-orders";
    }

    @GetMapping("/me/addresses")
    public String getMeAddresses(Model model) {
        model.addAttribute("currentPage", "addresses");
        model.addAttribute("user", userService.getCurrentUserDetail());
        model.addAttribute("addresses", addressService.getAddresses(0, 10).content());
        return "user/me-addresses";
    }

    @PostMapping("/me/addresses")
    public String createAddress(@ModelAttribute AddressCreateRequest request) {
        addressService.createAddress(request);
        return "redirect:/me/addresses";
    }

    @PostMapping("/me/addresses/{addressId}")
    public String updateAddress(@PathVariable long addressId, @ModelAttribute @Valid AddressUpdateRequest request) {
        addressService.updateAddress(addressId, request);
        return "redirect:/me/addresses";
    }

    @PutMapping("/me/addresses/{addressId}/default")
    public String setDefaultAddress(@PathVariable long addressId) {
        addressService.setDefaultAddress(addressId);
        return "redirect:/me/addresses";
    }

    @DeleteMapping("/me/addresses/{addressId}")
    public String deleteAddress(@PathVariable long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/me/addresses";
    }

    @GetMapping("/me/points")
    public String getMePoints(@RequestParam(required = false, defaultValue = "0") int page, Model model) {
        PageResponse<PointHistoryResponse> pointHistories = pointService.getPointHistories(page, 10, "createdAt,desc");
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pointHistories.page(), pointHistories.totalPages(), 5
        );

        model.addAttribute("currentPage", "points");
        model.addAttribute("user", userService.getCurrentUserDetail());
        model.addAttribute("pointHistories", pointHistories);
        model.addAttribute("pageInfo", pageInfo);

        return "user/me-points";
    }

    @GetMapping("/me/coupons")
    public String getMeCoupons(Model model) {
        model.addAttribute("currentPage", "coupons");
        model.addAttribute("user", userService.getCurrentUserDetail());
        return "user/me-coupons";
    }

    @GetMapping("/me/likes")
    public String getMeLikes(@RequestParam(required = false, defaultValue = "0") int page, Model model) {
        PageResponse<LikeResponse> likes = likeService.getCurrentUserLikes(page, 5, "id,desc");
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                likes.page(), likes.totalPages(), 5
        );

        model.addAttribute("currentPage", "likes");
        model.addAttribute("user", userService.getCurrentUserDetail());
        model.addAttribute("likes", likes);
        model.addAttribute("pageInfo", pageInfo);

        return "user/me-likes";
    }

    @DeleteMapping("/me/likes/{likeId}")
    public String deleteLike(@PathVariable long likeId) {
        likeService.deleteCurrentUserLike(likeId);
        return "redirect:/me/likes";
    }

    @GetMapping("/me/reviews")
    public String getMeReviews(@RequestParam(defaultValue = "0") int page,
        Model model) {

        PageResponse<ReviewListResponse> reviews =
            reviewClient.getReviewsByUserId(page, 5);

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            reviews.page(), reviews.totalPages(), 5);

        model.addAttribute("currentPage", "reviews");
        model.addAttribute("user",   userService.getCurrentUserDetail());
        model.addAttribute("reviews", reviews);
        model.addAttribute("pageInfo", pageInfo);

        return "user/me-reviews";
    }

    @PostMapping(value = "/me/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addReview(@RequestParam Long orderBookId,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam int rating,
        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        reviewClient.addReview(orderBookId, title, content, rating, images);
        return "redirect:/me/orders";
    }

    @PostMapping(value = "/me/reviews/{review-id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateMyReview(@PathVariable("review-id") Long reviewId,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam int rating,
        @RequestPart(value = "images", required = false)
        List<MultipartFile> images) {

        reviewClient.updateReview(reviewId, title, content, rating, images);
        return "redirect:/me/reviews";
    }

    @PostMapping("/me/reviews/{review-id}/delete")
    public String deleteMyReview(@PathVariable("review-id") Long reviewId) {
        reviewClient.deleteReview(reviewId);
        return "redirect:/me/reviews";
    }


    @PostMapping("/me/withdraw")
    public String postWithdraw(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            HttpServletResponse response
    ) {
        userService.withdrawCurrentUser();
        authService.logout(accessToken, response);
        return "redirect:/";
    }
}
