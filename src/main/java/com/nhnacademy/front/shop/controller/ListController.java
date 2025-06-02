package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ListController {

    private final BookClient bookClient;

    @GetMapping("/list")
    public String showBookList(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<BookResponse>> response = bookClient.getBooks();  // 페이징 미지원이더라도 PageResponse는 필요
        PageResponse<BookResponse> pageResponse = response.data();

        model.addAttribute("bookList", pageResponse.content()); // 리스트 자체
        model.addAttribute("books", pageResponse);              // 페이지 전체 정보
        return "admin/list";
    }
}