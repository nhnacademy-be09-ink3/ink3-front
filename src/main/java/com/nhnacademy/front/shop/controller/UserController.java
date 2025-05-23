package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.shop.user.client.dto.UserDetailResponse;
import com.nhnacademy.front.shop.user.dto.RegisterRequest;
import com.nhnacademy.front.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegister() {
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
        UserDetailResponse user = userService.getCurrentUserDetail();
        log.info(user.toString());
        model.addAttribute("user", user);
        return "user/me";
    }

    @GetMapping("/me/orders")
    public String getMeOrders(Model model) {
        model.addAttribute("currentPage", "orders");
        return "user/me";
    }

    @GetMapping("/me/addresses")
    public String getMeAddresses(Model model) {
        model.addAttribute("currentPage", "addresses");
        return "user/me-addresses";
    }

    @GetMapping("/me/points")
    public String getMePoints(Model model) {
        model.addAttribute("currentPage", "points");
        return "user/me-points";
    }

    @GetMapping("/me/coupons")
    public String getMeCoupons(Model model) {
        model.addAttribute("currentPage", "coupons");
        return "user/me-coupons";
    }

    @GetMapping("/me/likes")
    public String getMeLikes(Model model) {
        model.addAttribute("currentPage", "likes");
        return "user/me";
    }

    @GetMapping("/me/reviews")
    public String getMeReviews(Model model) {
        model.addAttribute("currentPage", "reviews");
        return "user/me";
    }

    @GetMapping("/me/withdraw")
    public String getMeWithdraw(Model model) {
        model.addAttribute("currentPage", "withdraw");
        return "user/me";
    }
}
