package com.nhnacademy.front.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.shop.author.client.AuthorClient;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.book.dto.AuthorDto;
import com.nhnacademy.front.shop.book.dto.AuthorRoleRequest;
import com.nhnacademy.front.shop.book.dto.BookCreateForm;
import com.nhnacademy.front.shop.book.dto.BookResponse;
import com.nhnacademy.front.shop.book.dto.BookStatus;
import com.nhnacademy.front.shop.book.dto.BookUpdateForm;
import com.nhnacademy.front.shop.book.dto.BookUpdateRequest;
import com.nhnacademy.front.shop.book.dto.BookCreateRequest;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import com.nhnacademy.front.shop.publisher.client.PublisherClient;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import com.nhnacademy.front.shop.tag.client.TagClient;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.MediaType;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.MainBookResponse;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewListResponse;
import com.nhnacademy.front.util.PageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookClient bookClient;
    private final ReviewClient reviewClient;
    private final CategoryClient categoryClient;
    private final AuthorClient authorClient;
    private final PublisherClient publisherClient;
    private final TagClient tagClient;
    private final ObjectMapper objectMapper;

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PathVariable Long bookId,
        @RequestParam(defaultValue = "0") int  page,
        @RequestParam(defaultValue = "10") int  size,
        Model model, @CookieValue(name = "accessToken", required = false) String accessToken) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        PageResponse<ReviewListResponse> reviews =
            reviewClient.getReviewsByBookId(bookId, page, size);

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
            reviews.page(), reviews.totalPages(), 5);

        Long userId = null;
        try {
            if (accessToken != null) {
                DecodedJWT decodedAccessToken = JWT.decode(accessToken);
                userId = decodedAccessToken.getClaim("id").asLong();
            }
        } catch (Exception e) {
            log.warn("비회원 사용자 도서 상세페이지 접근: {}", e.getMessage());
        }

        model.addAttribute("book",      response.data());
        model.addAttribute("reviews",   reviews.content());
        model.addAttribute("reviewPage", reviews);
        model.addAttribute("pageInfo",  pageInfo);
        model.addAttribute("bookId",    bookId);
        model.addAttribute("userId",    userId);

        return "book/book-detail";
    }

    @GetMapping("/books/bestseller")
    public String getBestsellerBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllBestsellerBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/bestseller-books";
    }

    @GetMapping("/books/new")
    public String getNewBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllNewBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/new-books";
    }

    @GetMapping("/books/recommend")
    public String getRecommendBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<MainBookResponse>> response = bookClient.getAllRecommendedBooks(page, 10);
        PageResponse<MainBookResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);

        return "book/list/recommend-books";
    }

    @GetMapping("/admin/book-register")
    public String getBookRegister(Model model) {
        CommonResponse<PageResponse<AuthorResponse>> authors = authorClient.getAuthors(0, 0);
        CommonResponse<PageResponse<PublisherResponse>> publishers = publisherClient.getPublishers(0, 0);
        CommonResponse<PageResponse<CategoryResponse>> categories = categoryClient.getCategories();
        CommonResponse<PageResponse<TagResponse>> tags = tagClient.getTags(0, 0);

        model.addAttribute("authors", authors.data().content());
        model.addAttribute("publishers", publishers.data());
        model.addAttribute("categories", categories.data().content());
        model.addAttribute("tags", tags.data());

        return "admin/book/book-register";
    }

    @PostMapping(value = "/admin/book-register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerBook(@ModelAttribute @Valid BookCreateForm bookCreateForm) {
        List<AuthorRoleRequest> authorRequests = new ArrayList<>();
        for (int i = 0; i < bookCreateForm.getAuthorIds().size(); i++) {
            Long authorId = bookCreateForm.getAuthorIds().get(i);
            String role = bookCreateForm.getAuthorRoles().get(i);
            authorRequests.add(new AuthorRoleRequest(authorId, role));
        }
        BookCreateRequest request = BookCreateRequest.from(bookCreateForm, authorRequests);

        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            bookClient.createBook(jsonRequest, bookCreateForm.getCoverImage());

        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return "redirect:/admin";
        }

        return "redirect:/admin/book-register";
    }

    @GetMapping("/books/category")
    public String booksByCategory(Model model) {
        CommonResponse<PageResponse<BookResponse>> response = bookClient.getBooks(0, 10);
        List<BookResponse> books = response.data().content();

        Map<Long, Integer> reviewCounts = new HashMap<>();
        Map<Long, Double> averageRatings = new HashMap<>();

        for (BookResponse book : books) {
            Long bookId = book.id();

            try {
                PageResponse<ReviewListResponse> page0 = reviewClient.getReviewsByBookId(bookId, 0, 1);
                int totalReviews = (int) page0.totalElements();
                reviewCounts.put(bookId, totalReviews);

                int totalPages = page0.totalPages();
                List<ReviewListResponse> allReviews = new ArrayList<>();

                for (int page = 0; page < totalPages; page++) {
                    PageResponse<ReviewListResponse> reviewPage = reviewClient.getReviewsByBookId(bookId, page, 100);
                    allReviews.addAll(reviewPage.content());
                }

                double avg = allReviews.stream()
                        .mapToDouble(ReviewListResponse::rating)
                        .average()
                        .orElse(0.0);
                averageRatings.put(bookId, avg);

            } catch (Exception e) {
                reviewCounts.put(bookId, 0);
                averageRatings.put(bookId, 0.0);
            }
        }

        model.addAttribute("bookList", books);
        model.addAttribute("reviewCounts", reviewCounts);
        model.addAttribute("averageRatings", averageRatings);

        return "book/category-list";
    }

    @GetMapping("/books/search")
    public String getSearchList() {
        return "book/search-list";
    }

    @GetMapping("/admin/book-edit/{book-id}")
    public String getBookEdit(@PathVariable(name="book-id") Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        CommonResponse<PageResponse<AuthorResponse>> authorList = authorClient.getAuthors(0, 0);
        CommonResponse<PageResponse<PublisherResponse>> publisherList = publisherClient.getPublishers(0, 0);
        CommonResponse<PageResponse<CategoryResponse>> categoryList = categoryClient.getCategories();
        CommonResponse<PageResponse<TagResponse>> tagList = tagClient.getTags(0, 0);
        List<Long> selectedTagIds = response.data().tags().stream()
                .map(TagResponse::id)
                .toList();
        List<AuthorDto> initialAuthors = response.data().authors();
        List<Long> selectedCategoryIds = response.data().categories().stream()
                .map(CategoryResponse::id)
                .toList();

        model.addAttribute("book", response.data());
        model.addAttribute("authors", authorList.data().content());
        model.addAttribute("publishers", publisherList.data().content());
        model.addAttribute("categories", categoryList.data().content());
        model.addAttribute("tags", tagList.data().content());
        model.addAttribute("selectedTagIds", selectedTagIds);
        model.addAttribute("initialAuthors", initialAuthors);
        model.addAttribute("selectedCategoryIds", selectedCategoryIds);
        model.addAttribute("statuses", Arrays.asList(BookStatus.values()));

        return "admin/book/book-edit";
    }


    @PutMapping("/admin/books/{bookId}")
    public String updateBook(
            @PathVariable("bookId") Long bookId,
            @ModelAttribute("book") @Valid BookUpdateForm bookUpdateForm,
            BindingResult bindingResult
    ) {

        List<AuthorRoleRequest> authorRequests = new ArrayList<>();
        for (int i = 0; i < bookUpdateForm.getAuthorIds().size(); i++) {
            Long aId = bookUpdateForm.getAuthorIds().get(i);
            String role = bookUpdateForm.getAuthorRoles().get(i);
            authorRequests.add(new AuthorRoleRequest(aId, role));
        }
        BookUpdateRequest request = new BookUpdateRequest(bookUpdateForm, authorRequests);

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult: {}", bindingResult);
            return "redirect:/admin/book-edit/" + bookId;
        }

        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            bookClient.updateBook(bookId, jsonRequest, bookUpdateForm.getCoverImage());

        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return "redirect:/admin";
        }

        return "redirect:/admin/book-edit/" + bookId;
    }

    @GetMapping("/admin/list")
    public String showBookList(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<BookResponse>> response = bookClient.getBooks(page, 10);
        PageResponse<BookResponse> books = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                books.page(), books.totalPages(), 5
        );

        model.addAttribute("currentPage", "list");
        model.addAttribute("books", books);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/book/list";
    }
}
