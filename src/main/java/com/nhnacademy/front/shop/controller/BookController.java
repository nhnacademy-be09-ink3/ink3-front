package com.nhnacademy.front.shop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final ReviewClient reviewClient;

    @GetMapping("/main")
    public String getBookMain() {
        return "book/book-main";
    }

    @GetMapping("/book/{bookId}")
    public String getBookDetail(@PageableDefault(size = 5) Pageable pageable, @PathVariable Long bookId,
        Model model) {
        PageResponse<ReviewResponse> reviews = reviewClient.getReviewsByBookId(pageable, bookId);
        model.addAttribute("reviews", reviews.content());
        model.addAttribute("page", reviews.page());
        model.addAttribute("size", reviews.size());
        model.addAttribute("bookId", bookId);
        return "book/book-detail";
    }
}
