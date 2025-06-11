package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.AladinClient;
import com.nhnacademy.front.shop.book.dto.AladinBookResponse;
import com.nhnacademy.front.shop.book.dto.BookRegisterRequest;
import com.nhnacademy.front.shop.tag.client.TagClient;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import com.nhnacademy.front.util.PageUtil;
import com.nhnacademy.front.util.PageUtil.PageInfo;
import jakarta.validation.Valid;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AladinController {

    private final AladinClient aladinClient;
    private final TagClient tagClient;

    @GetMapping("/admin/aladin/book-search")
    public String getBooksByAladin(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false, defaultValue = "0") int page,
                                   Model model) {

        PageResponse<AladinBookResponse> aladinBooks;

        if (keyword != null && !keyword.isBlank()) {
            CommonResponse<PageResponse<AladinBookResponse>> response = aladinClient.getBooksByKeyword(keyword, page, 10);
            aladinBooks = response.data();
        } else {
            // keyword 입력이 아직 없으면 빈 리스트
            aladinBooks = new PageResponse<>(Collections.emptyList(), 0, 10, 0L, 0, false, false);
        }

        PageInfo pageInfo = PageUtil.calculatePageRange(
                aladinBooks.page(), aladinBooks.totalPages(), 5
        );
        CommonResponse<PageResponse<TagResponse>> tags = tagClient.getTags(100, 0);

        model.addAttribute("currentPage", "aladinBooks");
        model.addAttribute("keyword", keyword);
        model.addAttribute("aladinBooks", aladinBooks);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", tags.data());

        return "admin/book/aladin-search";
    }

    @PostMapping("/admin/aladin/book-register")
    public String registerBookByAladin(@ModelAttribute @Valid BookRegisterRequest request) {
        aladinClient.registerBookByAladin(request);
        return "redirect:/admin/aladin/book-search";
    }
}
