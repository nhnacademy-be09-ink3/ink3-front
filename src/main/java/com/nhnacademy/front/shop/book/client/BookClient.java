package com.nhnacademy.front.shop.book.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.book.client.dto.BookCreateRequest;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bookClient", url = "${feign.url.shop}")
public interface BookClient {

    @PostMapping("/books")
    CommonResponse<BookResponse> createBook(@RequestBody BookCreateRequest request);

    @GetMapping("/books")
    CommonResponse<List<BookResponse>> getBooks();

    @GetMapping("/books/{bookId}")
    CommonResponse<BookResponse> getBookDetail(@PathVariable Long bookId);
}
