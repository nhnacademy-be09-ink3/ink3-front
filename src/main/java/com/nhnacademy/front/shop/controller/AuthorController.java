package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.author.client.AuthorClient;
import com.nhnacademy.front.shop.author.client.dto.AuthorCreateRequest;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.author.client.dto.AuthorUpdateRequest;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorClient authorClient;

    @PostMapping("/admin/authors")
    public String createAuthor(@ModelAttribute AuthorCreateRequest request) {
        authorClient.createAuthor(request);
        return "redirect:/admin/authors";
    }

    @GetMapping("/admin/authors")
    public String getAuthors(@RequestParam(required = false, defaultValue = "0") int page,
                                Model model) {
        CommonResponse<PageResponse<AuthorResponse>> response = authorClient.getAuthors(10, page);
        PageResponse<AuthorResponse> authors = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                authors.page(), authors.totalPages(), 5
        );

        model.addAttribute("currentPage", "authors");
        model.addAttribute("authors", authors);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/authors";
    }

    @PutMapping("/admin/authors/{authorId}")
    public String updateAuthor(@PathVariable Long authorId,
                            @ModelAttribute AuthorUpdateRequest request) {
        authorClient.updateAuthor(authorId, request);
        return "redirect:/admin/authors";
    }

    @DeleteMapping("/admin/authors/{authorId}")
    public String deleteAuthor(@PathVariable Long authorId) {
        authorClient.deleteAuthor(authorId);
        return "redirect:/admin/authors";
    }
}
