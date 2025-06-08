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
        @NotBlank String contents,
        @NotBlank String description,
        @NotNull @PastOrPresent LocalDate publishedAt,
        @NotNull @PositiveOrZero Integer originalPrice,
        @NotNull @PositiveOrZero Integer salePrice,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull BookStatus status,
        Boolean isPackable,
        @NotNull Long publisherId,

        @NotEmpty(message = "카테고리는 최소 1개 이상이어야 합니다.")
        List<@NotNull Long> categoryIds,
        @NotEmpty(message = "저자는 최소 1명 이상이어야 합니다.")
        List<AuthorRoleRequest> authors,
        List<@NotNull Long> tagIds
) {
        public BookUpdateRequest(BookUpdateForm form, List<AuthorRoleRequest> authorRequests) {
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
                        form.getPublisherId(),
                        form.getSelectedCategoryIds(),
                        authorRequests,
                        form.getTagIds()
                );
        }
}
