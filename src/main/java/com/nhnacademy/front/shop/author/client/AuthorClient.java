package com.nhnacademy.front.shop.author.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.author.client.dto.AuthorCreateRequest;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.author.client.dto.AuthorUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authorClient", url = "${feign.url.shop}")
public interface AuthorClient {

    @GetMapping("/authors")
    CommonResponse<PageResponse<AuthorResponse>> getAuthors(@RequestParam int size, @RequestParam int page);

    @PostMapping("/authors")
    CommonResponse<AuthorResponse> createAuthor(@RequestBody AuthorCreateRequest request);

    @PutMapping("/authors/{authorId}")
    CommonResponse<AuthorResponse> updateAuthor(@PathVariable Long authorId,
                                                @RequestBody AuthorUpdateRequest request);

    @DeleteMapping("/authors/{authorId}")
    CommonResponse<Void> deleteAuthor(@PathVariable Long authorId);
}
