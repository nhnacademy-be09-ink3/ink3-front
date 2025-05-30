package com.nhnacademy.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BookClient bookClient;

    // @GetMapping(value = {"/", "/home"})
    // public String index() {
    //     return "book/book-main";
    // }

    @GetMapping(value = {"/", "/home"})
    public String getBook(Model model) {
        List<BookResponse> allBooks = bookClient.getBooks().data().content();

        // 5개씩 슬라이싱
        List<BookResponse> bestSeller = sliceBooks(allBooks, 0, 5);
        List<BookResponse> newBooks = sliceBooks(allBooks, 5, 10);
        List<BookResponse> recommendBooks = sliceBooks(allBooks, 10, 15);

        model.addAttribute("bestSeller", bestSeller);
        model.addAttribute("newBooks", newBooks);
        model.addAttribute("recommendBooks", recommendBooks);

        return "book/book-main";
    }

    private List<BookResponse> sliceBooks(List<BookResponse> books, int from, int to) {
        int size = books.size();
        if (from >= size) {
            return List.of();
        }
        return books.subList(from, Math.min(to, size));
    }
}
