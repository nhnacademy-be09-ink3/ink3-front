package com.nhnacademy.front.shop.book.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookCreateRequest;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PathVariable Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        model.addAttribute("book", response.data());
        return "book/book-detail";
    }

    @GetMapping("/admin/books")
    public String getAdminBooks(Model model) {
        model.addAttribute("books", bookClient.getBooks().data());
        return "book/book-management";
    }

    @GetMapping("/book-register")
    public String getBookRegister() {
        return "book/book-register";
    }

    @GetMapping("/book-management")
    public String getBookManagement() {
        return "book/book-management";
    }
}
