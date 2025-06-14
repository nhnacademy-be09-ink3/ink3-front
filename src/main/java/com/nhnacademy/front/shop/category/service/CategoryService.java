package com.nhnacademy.front.shop.category.service;

import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryChangeParentRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryCreateRequest;
import com.nhnacademy.front.shop.category.client.dto.CategoryFlatDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryTreeDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryUpdateNameRequest;
import com.nhnacademy.front.shop.category.dto.CategoryDeleteRequest;
import com.nhnacademy.front.shop.category.dto.CategoryUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryClient categoryClient;

    public List<CategoryTreeDto> getAllCategoriesTree() {
        return categoryClient.getAllCategoriesTree().data();
    }

    public List<CategoryFlatDto> getAllCategoriesFlat() {
        return categoryClient.getAllCategoriesFlat().data();
    }

    public CategoryTreeDto getAllCDescendant(long id) {
        return categoryClient.getAllCDescendant(id).data();
    }

    public List<CategoryFlatDto> getAllAncestor(long id) {
        return categoryClient.getAllAncestor(id).data();
    }

    public CategoryTreeDto createCategory(CategoryCreateRequest request) {
        return categoryClient.createCategory(request).data();
    }

    public void updateCategory(CategoryUpdateRequest request) {
        categoryClient.updateName(request.id(), new CategoryUpdateNameRequest(request.name()));
    }

    public void changeParentCategory(long id, CategoryChangeParentRequest request) {
        categoryClient.changeParent(id, request);
    }

    public void deleteCategory(CategoryDeleteRequest request) {
        categoryClient.delete(request.id());
    }
}
