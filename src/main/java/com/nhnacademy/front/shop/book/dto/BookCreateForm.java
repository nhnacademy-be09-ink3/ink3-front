package com.nhnacademy.front.shop.book.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BookCreateForm {
    @NotBlank(message = "ISBN은 필수 입력값입니다.")
    private String isbn;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String contents;

    @NotBlank(message = "설명은 필수 입력값입니다.")
    private String description;

    @NotNull(message = "출간일을 입력해주세요.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishedAt;

    @NotNull(message = "원가는 필수 입력값입니다.")
    @Min(value = 0, message = "원가는 0 이상이어야 합니다.")
    private Integer originalPrice;

    @NotNull(message = "판매가는 필수 입력값입니다.")
    @Min(value = 0, message = "판매가는 0 이상이어야 합니다.")
    private Integer salePrice;

    @NotNull(message = "수량은 필수 입력값입니다.")
    @Min(value = 0, message = "수량은 0 이상이어야 합니다.")
    private Integer quantity;

    @NotNull(message = "상태를 선택해주세요.")
    private BookStatus status;

    private boolean packable = false;

    @NotNull(message = "출판사를 선택해주세요.")
    private Long publisherId;

    @NotEmpty(message = "카테고리를 최소 하나 이상 선택해주세요.")
    private List<@NotNull(message = "유효하지 않은 카테고리입니다.") Long> selectedCategoryIds = new ArrayList<>();

    @NotEmpty(message = "저자를 최소 하나 이상 입력해주세요.")
    private List<@NotNull(message = "유효하지 않은 저자입니다.") Long> authorIds = new ArrayList<>();

    @NotEmpty(message = "저자의 역할을 모두 입력해주세요.")
    private List<@NotBlank(message = "저자의 역할이 비어있습니다.") String> authorRoles = new ArrayList<>();

    private List<Long> tagIds = new ArrayList<>();

    @NotNull(message = "썸네일 이미지를 첨부해주세요.")
    private MultipartFile coverImage;
}
