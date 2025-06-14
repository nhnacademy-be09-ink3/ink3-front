package com.nhnacademy.front;

import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.shop.book.client.BookClient;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BookClient bookClient;

    @GetMapping(value = {"/", "/home"})
    public String getBook(Model model) {
        List<BookPreviewResponse> bestSeller = bookClient.getTop5BestsellerBooks().data().content();
        List<BookPreviewResponse> newBooks = bookClient.getTop5NewBooks().data().content();
        List<BookPreviewResponse> recommendBooks = bookClient.getTop5RecommendedBooks().data().content();

        model.addAttribute("bestSeller", bestSeller);
        model.addAttribute("newBooks", newBooks);
        model.addAttribute("recommendBooks", recommendBooks);

        return "book/book-main";
    }
}
