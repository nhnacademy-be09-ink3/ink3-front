package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookClient bookClient;

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

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PathVariable Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        model.addAttribute("book", response.data());
        return "book/book-detail";
    }
}
