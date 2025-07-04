package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record BookUpdateRequest(
        @NotBlank @Size(max = 13) String isbn,
        @NotBlank String title,
        String contents,
        @NotBlank String description,
        @NotNull @PastOrPresent LocalDate publishedAt,
        @NotNull @PositiveOrZero Integer originalPrice,
        @NotNull @PositiveOrZero Integer salePrice,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull BookStatus status,
        boolean isPackable,
        @NotBlank String publisher,

        @NotEmpty(message = "카테고리는 최소 1개 이상이어야 합니다.")
        List<@NotNull Long> categoryIds,
        @NotEmpty(message = "저자는 최소 1명 이상이어야 합니다.")
        List<BookAuthorDto> authors,
        List<@NotBlank String> tags
) {
        public BookUpdateRequest(BookUpdateForm form, List<BookAuthorDto> bookAuthors) {
                this(
                        form.getIsbn(),
                        form.getTitle(),
                        form.getContents(),
                        form.getDescription(),
                        form.getPublishedAt(),
                        form.getOriginalPrice(),
                        form.getSalePrice(),
                        form.getQuantity(),
                        form.getStatus(),
                        form.isPackable(),
                        form.getPublisherName(),
                        form.getCategoryIds(),
                        bookAuthors,
                        form.getTags()
                );
        }
}
