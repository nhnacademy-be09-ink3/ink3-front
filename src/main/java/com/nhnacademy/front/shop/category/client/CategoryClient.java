package com.nhnacademy.front.shop.category.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "categoryClient", url = "${feign.url.shop}")
public interface CategoryClient {

    @GetMapping("/categories/tree")
    CommonResponse<List<CategoryResponse>> getCategoryTree();

    @GetMapping("/categories")
    CommonResponse<PageResponse<CategoryResponse>> getCategories();

    @PostMapping("/categories")
    CommonResponse<CategoryResponse> createCategory(@ModelAttribute CategoryCreateRequest request);

    @DeleteMapping("/categories/{categoryId}")
    CommonResponse<Void> deleteCategory(@PathVariable Long categoryId);
}
