package com.nhnacademy.front.shop.search.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import com.nhnacademy.front.shop.search.client.BookSearchClient;
import com.nhnacademy.front.shop.search.client.BookSortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookSearchService {
    private final BookSearchClient bookSearchClient;
    private final BookClient bookClient;

    public PageResponse<BookPreviewResponse> searchBooksByKeyword(
            String keyword,
            int page,
            int size,
            BookSortOption sort
    ) {
        PageResponse<BookPreviewResponse> response = bookSearchClient.searchBooksByKeyword(
                keyword,
                page,
                size,
                sort.name()
        ).data();
        response.content().forEach(item -> bookClient.increaseSearchCount(item.id()));
        return response;
    }

    public PageResponse<BookPreviewResponse> searchBooksByCategory(
            String category,
            int page,
            int size,
            BookSortOption sort
    ) {
        PageResponse<BookPreviewResponse> response = bookSearchClient.searchBooksByCategory(
                category,
                page,
                size,
                sort.name()
        ).data();
        response.content().forEach(item -> bookClient.increaseSearchCount(item.id()));
        return response;
    }
}
