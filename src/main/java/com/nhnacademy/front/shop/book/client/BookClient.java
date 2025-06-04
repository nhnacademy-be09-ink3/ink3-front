package com.nhnacademy.front.shop.book.client;

import com.nhnacademy.front.shop.book.dto.BookResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.BookCreateRequest;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;

@FeignClient(name = "bookClient", url = "${feign.url.shop}")
public interface BookClient {

    @PostMapping("/books")
    CommonResponse<BookResponse> createBook(@RequestBody BookCreateRequest request);

    @GetMapping("/books")
    CommonResponse<PageResponse<BookResponse>> getBooks();

    @GetMapping("/books/bestseller")
    CommonResponse<PageResponse<MainBookResponse>> getTop5BestsellerBooks();

    @GetMapping("/books/bestseller-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllBestsellerBooks(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/new")
    CommonResponse<PageResponse<MainBookResponse>> getTop5NewBooks();

    @GetMapping("/books/new-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllNewBooks(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/recommend")
    CommonResponse<PageResponse<MainBookResponse>> getTop5RecommendedBooks();

    @GetMapping("/books/recommend-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllRecommendedBooks(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/{bookId}")
    CommonResponse<BookResponse> getBookDetail(@PathVariable Long bookId);
}
