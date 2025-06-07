package com.nhnacademy.front.shop.tag.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.tag.client.dto.TagCreateRequest;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import com.nhnacademy.front.shop.tag.client.dto.TagUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tagClient", url = "${feign.url.shop}")
public interface TagClient {

    @PostMapping("/tags")
    CommonResponse<TagResponse> createTag(@RequestBody TagCreateRequest request);

    @GetMapping("/tags")
    CommonResponse<PageResponse<TagResponse>> getTags(@RequestParam int size,
                                                      @RequestParam int page);

    @PutMapping("/tags/{tagId}")
    CommonResponse<TagResponse> updateTag(@PathVariable Long tagId,
                                          @RequestBody TagUpdateRequest request);

    @DeleteMapping("/tags/{tagId}")
    CommonResponse<Void> deleteTag(@PathVariable Long tagId);
}
