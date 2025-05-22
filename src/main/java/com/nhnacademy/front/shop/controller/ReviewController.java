package com.nhnacademy.front.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewRequest;
import com.nhnacademy.front.shop.review.dto.ReviewUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class ReviewController {
    private final ReviewClient reviewClient;

    @PostMapping("/{bookId}/reviews")
    public String addReview(@PathVariable Long bookId, Model model) {
        Long userId = 2L; //TODO: 실제로는 로그인 정보에서 가져와야 함
        Long orderBookId = 1L; //TODO: 실제 주문 도서 ID

        model.addAttribute("bookId", bookId);
        model.addAttribute("userId", userId);
        model.addAttribute("orderBookId", orderBookId);

        reviewClient.addReview(new ReviewRequest(userId, bookId, null, null, 0));
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{bookId}/reviews/{reviewId}/update")
    public String updateReview(@PathVariable Long bookId,
        @PathVariable Long reviewId,
        ReviewUpdateRequest request) {
        reviewClient.updateReview(reviewId, request);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{bookId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long bookId,
        @PathVariable Long reviewId) {
        reviewClient.deleteReview(reviewId);
        return "redirect:/books/" + bookId;
    }
}

