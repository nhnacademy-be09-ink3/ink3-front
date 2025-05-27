package com.nhnacademy.front.shop.book.controller;

import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookCreateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookClient bookClient;

    @GetMapping("/main")
    public String getBookMain() {
        return "book/book-main";
    }

    @GetMapping("/books")
    public String getBookDetail() {
        return "book/book-detail";
    }

    @PostMapping("/books")
    public String createBook(@RequestBody BookCreateRequest request) {
        bookClient.createBook(request);
        return "redirect:/book-management";
    }

    @GetMapping("/books/book-register")
    public String getBookRegister(Model model) {
        return "book/book-register";
    }

    @GetMapping("/book-management")
    public String getBookManagement() {
        return "book/book-management";
    }
}
