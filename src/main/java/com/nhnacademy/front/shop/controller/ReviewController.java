package com.nhnacademy.front.shop.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.MeReviewRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class ReviewController {
    private final ReviewClient reviewClient;

    @PostMapping(value = "/{book-id}/reviews/{review-id}/update",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateReview(@PathVariable(name = "book-id") Long bookId,
        @PathVariable(name = "review-id") Long reviewId,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam int rating,
        @RequestPart(value = "images", required = false)
        List<MultipartFile> images) {

        reviewClient.updateReview(reviewId, title, content, rating, images);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{book-id}/reviews/{review-id}/delete")
    public String deleteReview(@PathVariable(name = "book-id") Long bookId,
        @PathVariable(name = "review-id") Long reviewId) {
        reviewClient.deleteReview(reviewId);
        return "redirect:/books/" + bookId;
    }
}
