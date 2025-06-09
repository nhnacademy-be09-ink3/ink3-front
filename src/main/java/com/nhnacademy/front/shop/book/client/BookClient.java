package com.nhnacademy.front.shop.book.client;

import com.nhnacademy.front.shop.book.dto.BookResponse;
import com.nhnacademy.front.shop.book.dto.BookUpdateRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;
import com.nhnacademy.front.shop.book.dto.SortType;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "bookClient", url = "${feign.url.shop}")
public interface BookClient {

    @PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<BookResponse> createBook(
            @RequestPart("book") String bookCreateRequestJson,
            @RequestPart("coverImage") MultipartFile coverImage
    );

    @GetMapping("/books")
    CommonResponse<PageResponse<BookResponse>> getBooks(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/bestseller")
    CommonResponse<PageResponse<MainBookResponse>> getTop5BestsellerBooks();

    @GetMapping("/books/bestseller-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllBestsellerBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/new")
    CommonResponse<PageResponse<MainBookResponse>> getTop5NewBooks();

    @GetMapping("/books/new-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllNewBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/recommend")
    CommonResponse<PageResponse<MainBookResponse>> getTop5RecommendedBooks();

    @GetMapping("/books/recommend-all")
    CommonResponse<PageResponse<MainBookResponse>> getAllRecommendedBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/{bookId}")
    CommonResponse<BookResponse> getBookDetail(@PathVariable Long bookId);

    @PutMapping(value = "/books/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<BookResponse> updateBook(
            @PathVariable Long bookId,
            @RequestPart("book") String bookUpdateRequestJson,
            @RequestPart("coverImage") MultipartFile coverImage
    );
}
