package com.nhnacademy.front.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtil {
    private PageUtil() {
        throw new IllegalStateException("Utility class");
    }

    public record PageInfo(
            int startPage,
            int endPage
    ) {
    }

    public static PageInfo calculatePageRange(int currentPage, int totalPages, int maxPageLinks) {
        if (totalPages <= 0) {
            return new PageInfo(0, 0);
        }

        int startPage = Math.max(currentPage - maxPageLinks / 2, 0);
        int endPage = Math.min(startPage + maxPageLinks - 1, totalPages - 1);

        if (endPage - startPage + 1 < maxPageLinks) {
            startPage = Math.max(endPage - maxPageLinks + 1, 0);
        }
        log.info("startPage: {}, endPage: {}", startPage, endPage);
        return new PageInfo(startPage, endPage);
    }
}
