package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.publisher.client.PublisherClient;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherCreateRequest;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherUpdateRequest;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublisherController {

    private final PublisherClient publisherClient;

    @PostMapping("/admin/pubs")
    public String createPublisher(@ModelAttribute PublisherCreateRequest request) {
        publisherClient.createPublisher(request);
        return "redirect:/admin/pubs";
    }

    @GetMapping("/admin/pubs")
    public String getPublishers(@RequestParam(required = false, defaultValue = "0") int page,
                                Model model) {
        CommonResponse<PageResponse<PublisherResponse>> response = publisherClient.getPublishers(10, page);
        PageResponse<PublisherResponse> publishers = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                publishers.page(), publishers.totalPages(), 5
        );

        model.addAttribute("currentPage", "publishers");
        model.addAttribute("publishers", publishers);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/publishers";
    }

    @PutMapping("/admin/pubs/{publisherId}")
    public String updatePublisher(@PathVariable Long publisherId,
                            @ModelAttribute PublisherUpdateRequest request) {
        publisherClient.updatePublisher(publisherId, request);
        return "redirect:/admin/pubs";
    }

    @DeleteMapping("/admin/pubs/{publisherId}")
    public String deletePublisher(@PathVariable Long publisherId) {
        publisherClient.deletePublisher(publisherId);
        return "redirect:/admin/pubs";
    }
}
