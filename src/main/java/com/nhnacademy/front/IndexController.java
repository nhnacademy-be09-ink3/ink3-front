package com.nhnacademy.front;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BookClient bookClient;

    @GetMapping(value = {"/", "/home"})
    public String getBook(Model model) {
        List<MainBookResponse> bestSeller = bookClient.getTop5BestsellerBooks().data().content();
        List<MainBookResponse> newBooks = bookClient.getTop5NewBooks().data().content();
        List<MainBookResponse> recommendBooks = bookClient.getTop5RecommendedBooks().data().content();

        model.addAttribute("bestSeller", bestSeller);
        model.addAttribute("newBooks", newBooks);
        model.addAttribute("recommendBooks", recommendBooks);

        return "book/book-main";
    }
}
