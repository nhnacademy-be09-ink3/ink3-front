package com.nhnacademy.front.shop.review.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ReviewFormUpdateRequest(
    String title,
    String content,
    int rating,
    List<MultipartFile> images
) {
    public ReviewUpdateRequest toRequest() {
        return new ReviewUpdateRequest(title, content, rating);
    }
}
