package com.nhnacademy.front.shop.category.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "categoryClient", url = "${feign.url.shop}")
public interface CategoryClient {

    @GetMapping("/categories/tree")
    CommonResponse<List<CategoryResponse>> getCategoryTree();

    @GetMapping("/categories")
    CommonResponse<PageResponse<CategoryResponse>> getCategories();
}
