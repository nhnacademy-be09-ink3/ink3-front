package com.nhnacademy.front.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    @GetMapping("/main")
    public String getBookMain() {
        return "book/book-main";
    }

    @GetMapping("/book")
    public String getBookDetail() {
        return "book/book-detail";
    }
}
