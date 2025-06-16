package com.nhnacademy.front.shop.search.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookSearchClient", url = "${feign.url.shop}/search/books")
public interface BookSearchClient {
    @GetMapping("/by-keyword")
    CommonResponse<PageResponse<BookPreviewResponse>> searchBooksByKeyword(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "POPULARITY") String sort
    );

    @GetMapping("/by-category")
    CommonResponse<PageResponse<BookPreviewResponse>> searchBooksByCategory(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "POPULARITY") String sort
    );
}
