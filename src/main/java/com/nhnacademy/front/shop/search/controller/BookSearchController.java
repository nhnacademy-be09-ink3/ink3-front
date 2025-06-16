package com.nhnacademy.front.shop.search.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import com.nhnacademy.front.shop.search.client.BookSortOption;
import com.nhnacademy.front.shop.search.service.BookSearchService;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/search/books")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping("/by-category")
    public String searchBooksByCategory(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "POPULARITY") BookSortOption sort,
            Model model
    ) {
        PageResponse<BookPreviewResponse> books = bookSearchService.searchBooksByCategory(category, page, 10, sort);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                books.page(), books.totalPages(), 5
        );
        model.addAttribute("books", books);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("category", category);
        model.addAttribute("sorts", BookSortOption.values());
        model.addAttribute("currentSort", sort);
        return "book/search/search-books-by-category";
    }

    @GetMapping("/by-keyword")
    public String searchBooksByKeyword(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "POPULARITY") BookSortOption sort,
            Model model
    ) {
        PageResponse<BookPreviewResponse> books = bookSearchService.searchBooksByKeyword(keyword, page, 10, sort);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                books.page(), books.totalPages(), 5
        );
        model.addAttribute("books", books);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sorts", BookSortOption.values());
        model.addAttribute("currentSort", sort);
        return "book/search/search-books-by-keyword";
    }
}
