package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.publisher.client.PublisherClient;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherCreateRequest;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublisherController {

    private final PublisherClient publisherClient;

    @PostMapping("/publishers")
    public String createPublisher(@RequestBody PublisherCreateRequest request) {
        CommonResponse<PublisherResponse> response = publisherClient.createPublisher(request);
        log.info("Create publisher response: {}", response);
        return "redirect:/admin";
    }

    @GetMapping("/publishers")
    public String getPublishers(@RequestParam int size,
                                @RequestParam int page,
                                Model model) {
        CommonResponse<PageResponse<PublisherResponse>> response = publisherClient.getPublishers(size, page);
        log.info("Get publishers response: {}", response);
        model.addAttribute("publishers", response.data());
        return "/book/admin";
    }
}
