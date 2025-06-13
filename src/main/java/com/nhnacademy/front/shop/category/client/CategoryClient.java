package com.nhnacademy.front.shop.category.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.category.client.dto.CategoryChangeParentRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryFlatDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryTreeDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryUpdateNameRequest;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "categoryClient", url = "${feign.url.shop}/categories")
public interface CategoryClient {
    @GetMapping("/tree")
    CommonResponse<List<CategoryTreeDto>> getAllCategoriesTree();

    @GetMapping("/flat")
    CommonResponse<List<CategoryFlatDto>> getAllCategoriesFlat();

    @GetMapping("/{id}/descendants")
    CommonResponse<CategoryTreeDto> getAllCDescendant(@PathVariable Long id);

    @GetMapping("/{id}/ancestor")
    CommonResponse<List<CategoryFlatDto>> getAllAncestor(@PathVariable Long id);

    @PostMapping
    CommonResponse<CategoryTreeDto> createCategory(@RequestBody CategoryCreateRequest request);

    @PatchMapping("/{id}/name")
    void updateName(@PathVariable long id, @RequestBody CategoryUpdateNameRequest request);

    @PatchMapping("/{id}/parent")
    void changeParent(@PathVariable long id, @RequestBody CategoryChangeParentRequest request);

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id);
}
