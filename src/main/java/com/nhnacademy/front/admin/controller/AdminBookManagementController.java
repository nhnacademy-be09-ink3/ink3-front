package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.dto.CategoryDeleteRequest;
import com.nhnacademy.front.shop.category.dto.CategoryUpdateRequest;
import com.nhnacademy.front.shop.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminBookManagementController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getCategoryManagement(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesTree());
        return "admin/book/category-management";
    }

    @PostMapping("/category/create")
    public String createCategory(@ModelAttribute @Valid CategoryCreateRequest request) {
        categoryService.createCategory(request);
        return "redirect:/admin/category";
    }

    @PostMapping("/category/update")
    public String updateCategory(@ModelAttribute @Valid CategoryUpdateRequest request) {
        categoryService.updateCategory(request);
        return "redirect:/admin/category";
    }

    @PostMapping("/category/delete")
    public String deleteCategory(@ModelAttribute CategoryDeleteRequest request) {
        categoryService.deleteCategory(request);
        return "redirect:/admin/category";
    }
}
