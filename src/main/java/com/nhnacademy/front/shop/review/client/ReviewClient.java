package com.nhnacademy.front.shop.review.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.review.dto.ReviewRequest;
import com.nhnacademy.front.shop.review.dto.ReviewResponse;
import com.nhnacademy.front.shop.review.dto.ReviewUpdateRequest;

@FeignClient(name = "reviewClient", url = "${feign.url.shop}")
public interface ReviewClient {
    @PostMapping(value = "/reviews", consumes = "multipart/form-data")
    ReviewResponse addReview(
        @RequestPart("review") ReviewRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
    );

    @GetMapping("/users/{user-id}/reviews")
    ReviewResponse getReviewByUserId(@PathVariable(name = "user-id") Long userId);

    @GetMapping("/books/{book-id}/reviews")
    PageResponse<ReviewResponse> getReviewsByBookId(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable,
        @PathVariable(name = "book-id") Long bookId
    );

    @PutMapping(value = "/reviews/{review-id}", consumes = "multipart/form-data")
    ReviewResponse updateReview(
        @PathVariable(name = "review-id") Long reviewId,
        @RequestPart("review") ReviewUpdateRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
    );

    @DeleteMapping("/reviews/{review-id}")
    void deleteReview(@PathVariable(name = "review-id") Long reviewId);
}
