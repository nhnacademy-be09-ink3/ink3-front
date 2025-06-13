package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryFlatDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryTreeDto;
import com.nhnacademy.front.shop.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryClient categoryClient;
    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        List<CategoryTreeDto> categoryTree = categoryService.getAllCategoriesTree();
        List<CategoryFlatDto> categories = categoryService.getAllCategoriesFlat();

        model.addAttribute("categoryTree", categoryTree);
        model.addAttribute("categories", categories);

        return "admin/book/categories";
    }

    @PostMapping("/admin/categories")
    public String createCategory(@ModelAttribute CategoryCreateRequest request) {
        categoryClient.createCategory(request);
        return "redirect:/admin/categories";
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryClient.delete(categoryId);
        return "redirect:/admin/categories";
    }
}
