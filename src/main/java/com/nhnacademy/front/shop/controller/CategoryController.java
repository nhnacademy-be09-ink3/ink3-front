package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryClient categoryClient;

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        CommonResponse<List<CategoryResponse>> categoryTree = categoryClient.getCategoryTree();
        CommonResponse<PageResponse<CategoryResponse>> categories = categoryClient.getCategories();

        model.addAttribute("categoryTree", categoryTree.data());
        model.addAttribute("categories", categories.data());

        return "admin/categories";
    }

    @PostMapping("/admin/categories")
    public String createCategory(@ModelAttribute CategoryCreateRequest request) {
        categoryClient.createCategory(request);
        return "redirect:/admin/categories";
    }
}
