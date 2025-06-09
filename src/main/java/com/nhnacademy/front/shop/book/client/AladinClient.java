package com.nhnacademy.front.shop.book.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.AladinBookResponse;
import com.nhnacademy.front.shop.book.dto.BookRegisterRequest;
import com.nhnacademy.front.shop.book.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "aladinClient", url = "${feign.url.shop}")
public interface AladinClient {

    @GetMapping("/aladin")
    CommonResponse<PageResponse<AladinBookResponse>> getBooksByKeyword(@RequestParam String keyword, @RequestParam int page, @RequestParam int size);

    @PostMapping(value = "/aladin/register-book")
    CommonResponse<BookResponse> registerBookByAladin(@RequestBody @Valid BookRegisterRequest request);
}
