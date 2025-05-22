package com.nhnacademy.front.shop.review.client;

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

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.review.dto.ReviewRequest;
import com.nhnacademy.front.shop.review.dto.ReviewResponse;
import com.nhnacademy.front.shop.review.dto.ReviewUpdateRequest;

@FeignClient(name = "reviewClient", url = "${feign.url.shop}")
public interface ReviewClient {
    @PostMapping("/reviews")
    ReviewResponse addReview(@RequestBody ReviewRequest request);

    @GetMapping("/reviews/user/{userId}")
    ReviewResponse getReviewByUserId(@PathVariable Long userId);

    @GetMapping("/reviews/book/{bookId}")
    PageResponse<ReviewResponse> getReviewsByBookId(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable, @PathVariable Long bookId);

    @PutMapping("/reviews/{reviewId}")
    ReviewResponse updateReview(@PathVariable Long reviewId,
        @RequestBody ReviewUpdateRequest request);

    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable Long id);
}
