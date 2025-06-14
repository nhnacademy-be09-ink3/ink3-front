package com.nhnacademy.front.shop.book.client;

import com.nhnacademy.front.shop.book.dto.AdminBookResponse;
import com.nhnacademy.front.shop.book.dto.BookDetailResponse;
import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.SortType;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "bookClient", url = "${feign.url.shop}")
public interface BookClient {

    @GetMapping("/books/{bookId}")
    CommonResponse<BookDetailResponse> getBookByIdWithParentCategory(@PathVariable Long bookId);

    @GetMapping("/books")
    CommonResponse<PageResponse<BookPreviewResponse>> getBooks(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/admin")
    CommonResponse<PageResponse<AdminBookResponse>> getBooksByAdmin(@RequestParam int page, @RequestParam int size);

    @GetMapping("/books/bestseller")
    CommonResponse<PageResponse<BookPreviewResponse>> getTop5BestsellerBooks();

    @GetMapping("/books/bestseller-all")
    CommonResponse<PageResponse<BookPreviewResponse>> getAllBestsellerBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/new")
    CommonResponse<PageResponse<BookPreviewResponse>> getTop5NewBooks();

    @GetMapping("/books/new-all")
    CommonResponse<PageResponse<BookPreviewResponse>> getAllNewBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/recommend")
    CommonResponse<PageResponse<BookPreviewResponse>> getTop5RecommendedBooks();

    @GetMapping("/books/recommend-all")
    CommonResponse<PageResponse<BookPreviewResponse>> getAllRecommendedBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType, @RequestParam int page, @RequestParam int size);

    @PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<BookDetailResponse> createBook(
            @RequestPart("book") String bookCreateRequestJson,
            @RequestPart("coverImage") MultipartFile coverImage
    );

    @PutMapping(value = "/books/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<BookDetailResponse> updateBook(
            @PathVariable Long bookId,
            @RequestPart("book") String bookUpdateRequestJson,
            @RequestPart("coverImage") MultipartFile coverImage
    );

    @DeleteMapping("/books/{bookId}")
    CommonResponse<Void> deleteBook(@PathVariable Long bookId);
}