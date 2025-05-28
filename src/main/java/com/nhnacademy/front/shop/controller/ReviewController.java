package com.nhnacademy.front.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewFormRequest;
import com.nhnacademy.front.shop.review.dto.ReviewFormUpdateRequest;
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

    @PostMapping("books/{bookId}/reviews")
    public String addReview(@PathVariable Long bookId,
        @ModelAttribute ReviewFormRequest form) {
        Long userId = 2L; // TODO: 실제 로그인 유저 정보
        Long orderBookId = 1L; // TODO: 실제 주문 도서 ID

        reviewClient.addReview(form.toRequest(userId, orderBookId), form.images());
        return "redirect:/books/" + bookId;
    }

    @PostMapping("books/{bookId}/reviews/{reviewId}")
    public String updateReview(@PathVariable Long bookId,
        @PathVariable Long reviewId,
        @ModelAttribute ReviewFormUpdateRequest form) {

        reviewClient.updateReview(reviewId, form.toRequest(), form.images());
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("books/{bookId}/reviews/{reviewId}")
    public String deleteReview(@PathVariable Long bookId,
        @PathVariable Long reviewId) {
        reviewClient.deleteReview(reviewId);
        return "redirect:/books/" + bookId;
    }
}
