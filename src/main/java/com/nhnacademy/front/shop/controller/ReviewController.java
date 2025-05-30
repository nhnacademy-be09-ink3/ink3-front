package com.nhnacademy.front.shop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewFormUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class ReviewController {
    private final ReviewClient reviewClient;

    // @PostMapping("/{book-id}/reviews")
    // public String addReview(@PathVariable(name = "book-id") Long bookId, @ModelAttribute ReviewFormRequest form) {
    //     reviewClient.addReview(form.toRequest(), form.images());
    //     return "redirect:/books/" + bookId;
    // }

    @PostMapping("/{bookId}/reviews/{reviewId}")
    public String updateReview(@PathVariable Long bookId,
        @PathVariable Long reviewId,
        @ModelAttribute ReviewFormUpdateRequest form, List<MultipartFile> images) {
        reviewClient.updateReview(reviewId, form.toRequest(), images);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{book-id}/reviews/{review-id}")
    public String deleteReview(@PathVariable(name = "book-id") Long bookId,
        @PathVariable(name = "review-id") Long reviewId) {
        reviewClient.deleteReview(reviewId);
        return "redirect:/books/" + bookId;
    }
}
