package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.tag.client.TagClient;
import com.nhnacademy.front.shop.tag.client.dto.TagCreateRequest;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import com.nhnacademy.front.shop.tag.client.dto.TagUpdateRequest;
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
public class TagController {

    private final TagClient tagClient;

    @PostMapping("/admin/tags")
    public String createTag(@ModelAttribute TagCreateRequest request) {
        tagClient.createTag(request);
        return "redirect:/admin/tags";
    }


    @GetMapping("/admin/tags")
    public String getTags(@RequestParam(required = false, defaultValue = "0") int page,
                          Model model) {
        CommonResponse<PageResponse<TagResponse>> response = tagClient.getTags(10, page);
        PageResponse<TagResponse> tags = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                tags.page(), tags.totalPages(), 5
        );

        model.addAttribute("currentPage", "tags");
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/tags";
    }

    @PutMapping("/admin/tags/{tagId}")
    public String updateTag(@PathVariable Long tagId,
                            @ModelAttribute TagUpdateRequest request) {
        tagClient.updateTag(tagId, request);
        return "redirect:/admin/tags";
    }

    @DeleteMapping("/admin/tags/{tagId}")
    public String deleteTag(@PathVariable Long tagId) {
        tagClient.deleteTag(tagId);
        return "redirect:/admin/tags";
    }
}
