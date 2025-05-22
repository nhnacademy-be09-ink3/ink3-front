package com.nhnacademy.front.shop.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookClient bookClient;
    private final ReviewClient reviewClient;

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PageableDefault(size = 5) Pageable pageable, @PathVariable Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        PageResponse<ReviewResponse> reviews = reviewClient.getReviewsByBookId(pageable, bookId);
        model.addAttribute("reviews", reviews.content());
        model.addAttribute("page", reviews.page());
        model.addAttribute("size", reviews.size());
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", response.data());
        return "book/book-detail";
    }

    @GetMapping("/book-register")
    public String getBookRegister() {
        return "book/book-register";
    }

    @GetMapping("/books/category")
    public String getCategoryList() {
        return "book/category-list";
    }

    @GetMapping("/books/search")
    public String getSearchList() {
        return "book/search-list";
    }
}
