package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.book.client.AladinClient;
import com.nhnacademy.front.shop.book.dto.AladinBookResponse;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AladinController {

    private final AladinClient aladinClient;

    @GetMapping("/admin/aladin-search")
    public String getAladinBookRegister() {
        return "admin/book/aladin-search";
    }


    @GetMapping("/admin/aladin")
    public String getBooksByAladin(@RequestParam String keyword,
                                   @RequestParam(required = false, defaultValue = "0") int page,
                                   Model model) {
        CommonResponse<PageResponse<AladinBookResponse>> response = aladinClient.getBooksByKeyword(keyword, page, 10);
        PageResponse<AladinBookResponse> aladinBooks = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                aladinBooks.page(), aladinBooks.totalPages(), 5
        );

        model.addAttribute("currentPage", "aladinBooks");
        model.addAttribute("aladinBooks", aladinBooks);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/book/aladin-register";
    }
}
