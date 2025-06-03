package com.nhnacademy.front.shop.publisher.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherCreateRequest;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "publisherClient", url = "${feign.url.shop}")
public interface PublisherClient {

    @PostMapping("/pubs")
    CommonResponse<PublisherResponse> createPublisher(@RequestBody PublisherCreateRequest request);

    @GetMapping("/pubs")
    CommonResponse<PageResponse<PublisherResponse>> getPublishers(@RequestParam int size,
                                                                  @RequestParam int page);

    @PutMapping("/pubs/{publisherId}")
    CommonResponse<PublisherResponse> updatePublisher(@PathVariable Long publisherId,
                                                      @RequestBody PublisherUpdateRequest request);

    @DeleteMapping("/pubs/{publisherId}")
    Void deletePublisher(@PathVariable Long publisherId);
}
