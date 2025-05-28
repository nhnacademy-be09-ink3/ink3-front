package com.nhnacademy.front.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.BookResponse;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewListResponse;
import com.nhnacademy.front.util.PageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookClient bookClient;
    private final ReviewClient reviewClient;

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PathVariable Long bookId,
        @RequestParam(defaultValue = "0") int  page,
        @RequestParam(defaultValue = "10") int  size,
        Model model, @CookieValue(name = "accessToken", required = false) String accessToken) {
        CommonResponse<BookResponse> books = bookClient.getBookDetail(bookId);

        PageResponse<ReviewListResponse> reviews =
            reviewClient.getReviewsByBookId(bookId, page, size);

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            reviews.page(), reviews.totalPages(), 5);

        Long userId = null;
        try {
            if (accessToken != null) {
                DecodedJWT decodedAccessToken = JWT.decode(accessToken);
                userId = decodedAccessToken.getClaim("id").asLong();
            }
        } catch (Exception e) {
            log.warn("비회원 사용자 도서 상세페이지 접근: {}", e.getMessage());
        }

        model.addAttribute("book",      books.data());
        model.addAttribute("reviews",   reviews.content());
        model.addAttribute("reviewPage", reviews);
        model.addAttribute("pageInfo",  pageInfo);
        model.addAttribute("bookId",    bookId);
        model.addAttribute("userId",    userId);

        return "book/book-detail";
    }

    @GetMapping("/books/bestseller")
    public String getBestsellerBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllBestsellerBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/bestseller-books";
    }

    @GetMapping("/books/new")
    public String getNewBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllNewBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/new-books";
    }

    @GetMapping("/books/recommend")
    public String getRecommendBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllRecommendedBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/recommend-books";
    }

    @GetMapping("/book-register")
    public String getBookRegister() {
        return "book/book-register";
    }

    @GetMapping("/books/category/{categoryName}")
    public String getCategoryList(@PathVariable String categoryName, Model model) {
        model.addAttribute("categoryName", categoryName);
        return "book/category-list";
    }

    @GetMapping("/books/search")
    public String getSearchList() {
        return "book/search-list";
    }
}
