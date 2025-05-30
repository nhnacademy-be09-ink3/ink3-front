package com.nhnacademy.front.shop.review.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.review.dto.MeReviewRequest;
import com.nhnacademy.front.shop.review.dto.ReviewListResponse;
import com.nhnacademy.front.shop.review.dto.ReviewResponse;
import com.nhnacademy.front.shop.review.dto.ReviewUpdateRequest;

import jakarta.validation.Valid;

@FeignClient(name = "reviewClient", url = "${feign.url.shop}")
public interface ReviewClient {
    @PostMapping(value = "/me/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<ReviewResponse> addReview(
        @RequestPart("review") @Valid MeReviewRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images);

    @PostMapping(value = "/me/reviews/{review-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse<ReviewResponse> updateReview(@PathVariable(name = "review-id") Long reviewId,
        @RequestPart("review") @Valid ReviewUpdateRequest reviewUpdateRequest,
        @RequestPart(value = "images", required = false) List<MultipartFile> images);

    @GetMapping("/me/users/reviews")
    PageResponse<ReviewListResponse> getReviewsByUserId(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable);

    @GetMapping("/me/books/{book-id}/reviews")
    PageResponse<ReviewListResponse> getReviewsByBookId(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        @PathVariable(name = "book-id") Long bookId);

    @DeleteMapping("/me/reviews/{review-id}")
    void deleteReview(@PathVariable(name = "review-id") Long reviewId);
}
