package com.nhnacademy.front.shop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.author.client.AuthorClient;
import com.nhnacademy.front.shop.book.dto.AdminBookResponse;
import com.nhnacademy.front.shop.book.dto.BookAuthorDto;
import com.nhnacademy.front.shop.book.dto.BookCreateForm;
import com.nhnacademy.front.shop.book.dto.BookCreateRequest;
import com.nhnacademy.front.shop.book.dto.BookDetailResponse;
import com.nhnacademy.front.shop.book.dto.BookPreviewResponse;
import com.nhnacademy.front.shop.book.dto.BookStatus;
import com.nhnacademy.front.shop.book.dto.BookUpdateForm;
import com.nhnacademy.front.shop.book.dto.BookUpdateRequest;
import com.nhnacademy.front.shop.book.dto.SortType;
import com.nhnacademy.front.shop.category.client.dto.CategoryFlatDto;
import com.nhnacademy.front.shop.category.client.dto.CategoryTreeDto;
import com.nhnacademy.front.shop.category.service.CategoryService;
import com.nhnacademy.front.shop.coupon.coupon.client.CouponClient;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponResponse;
import com.nhnacademy.front.shop.coupon.coupon.client.dto.CouponView;
import com.nhnacademy.front.shop.coupon.store.client.CouponStore;
import com.nhnacademy.front.shop.coupon.store.client.dto.CouponIssueRequest;
import com.nhnacademy.front.shop.coupon.store.client.dto.StoresResponse;
import com.nhnacademy.front.shop.like.client.dto.LikeResponse;
import com.nhnacademy.front.shop.like.service.LikeService;
import com.nhnacademy.front.shop.review.client.ReviewClient;
import com.nhnacademy.front.shop.review.dto.ReviewListResponse;
import com.nhnacademy.front.util.PageUtil;

import feign.FeignException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookClient bookClient;
    private final ReviewClient reviewClient;
    private final ObjectMapper objectMapper;
    private final CouponStore couponStore;
    private final CouponClient couponClient;
    private final LikeService likeService;
    private final CategoryService categoryService;


    @GetMapping("/books/{bookId}")
    public String getBookDetail(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            @CookieValue(name = "accessToken", required = false) String accessToken
    ) {
        CommonResponse<BookDetailResponse> books = bookClient.getBookByIdWithParentCategory(bookId);
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

        List<CouponResponse> bookCoupons = new ArrayList<>();
        List<CouponResponse> categoryCoupons = new ArrayList<>();
        List<CouponResponse> parentCategoryCoupons = new ArrayList<>();


        // 1) 책 쿠폰
        try {
            bookCoupons = couponClient.getByBookId(bookId, 0, 20).data().content();
        } catch (FeignException e) {
            log.error("책 쿠폰 조회 실패: {}", e.getMessage());
        }

        // 2) 카테고리 쿠폰
        try {
            categoryCoupons = books.data().categories().stream()
                    .flatMap(cat -> couponClient.getByCategoryId(cat.getFirst().id(), 0, 20).data().content().stream())
                    .toList();
        } catch (FeignException e) {
            log.error("카테고리 쿠폰 조회 실패: {}", e.getMessage());
        }

        // 3) 부모 카테고리 쿠폰
        try{
            parentCategoryCoupons = couponClient.getParentCategoryCoupons(bookId, 0, 20).data().content();
            log.info("부모카테고리 id: {}", parentCategoryCoupons);
        }catch (FeignException e){
            log.error("부모 카테고리 쿠폰 조회 실패: {}", e.getMessage());
        }

        // 3) 세 리스트 합치기 & 중복 제거(optional)
        List<CouponResponse> coupons = Stream.of(
                        bookCoupons.stream(),
                        categoryCoupons.stream(),
                        parentCategoryCoupons.stream()
                )
                .flatMap(s -> s)
                .distinct()
                .toList();

        List<CouponView> flatCoupons = coupons.stream()
                .map(c -> {
                    if (!c.books().isEmpty()) {
                        var b = c.books().getFirst();
                        return new CouponView(
                                c.couponId(), c.name(),
                                c.discountRate(), c.discountValue(),
                                c.expiresAt(), c.isActive(),
                                b.originType(), b.originId());
                    }
                    if (!c.categories().isEmpty()) {            // CATEGORY 쿠폰
                        var cat = c.categories().getFirst();
                        return new CouponView(
                                c.couponId(), c.name(),
                                c.discountRate(), c.discountValue(),
                                c.expiresAt(), c.isActive(),
                                cat.originType(), cat.originId());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        model.addAttribute("coupons", flatCoupons);


        AtomicBoolean liked = new AtomicBoolean(false);
        AtomicReference<Long> likeId = new AtomicReference<>(null);

        if (userId != null) {
            PageResponse<LikeResponse> likes = likeService.getCurrentUserLikes(0, 100, null);
            likes.content().stream()
                    .filter(like -> like.bookId().equals(bookId))
                    .findFirst()
                    .ifPresent(like -> {
                        liked.set(true);
                        likeId.set(like.id());
                    });
        }

        bookClient.increaseViewCount(bookId);

        model.addAttribute("book", books.data());
        model.addAttribute("reviews", reviews.content());
        model.addAttribute("reviewPage", reviews);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("bookId", bookId);
        model.addAttribute("userId", userId);
        model.addAttribute("liked", liked);
        model.addAttribute("likeId", likeId);
        model.addAttribute("coupons", flatCoupons);

        return "book/book-detail";
    }

    @GetMapping("/books/bestseller")
    public String getBestsellerBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType,
                                     @RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<BookPreviewResponse>> response = bookClient.getAllBestsellerBooks(sortType, page,
                10);
        PageResponse<BookPreviewResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentSort", sortType.name());

        return "book/list/bestseller-books";
    }

    @GetMapping("/books/new")
    public String getNewBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType,
                              @RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<BookPreviewResponse>> response = bookClient.getAllNewBooks(sortType, page, 10);
        PageResponse<BookPreviewResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentSort", sortType.name());

        return "book/list/new-books";
    }

    @GetMapping("/books/recommend")
    public String getRecommendBooks(@RequestParam(defaultValue = "REVIEW") SortType sortType,
                                    @RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<BookPreviewResponse>> response = bookClient.getAllRecommendedBooks(sortType, page,
                10);
        PageResponse<BookPreviewResponse> pageData = response.data();

        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pageData.page(), pageData.totalPages(), 5
        );

        model.addAttribute("bookList", pageData.content());
        model.addAttribute("bookPage", pageData);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentSort", sortType.name());

        return "book/list/recommend-books";
    }

    @GetMapping("/admin/book-register")
    public String getBookRegister(Model model) {
        List<CategoryTreeDto> categories = categoryService.getAllCategoriesTree();

        model.addAttribute("categories", categories);

        return "admin/book/book-register";
    }

    @PostMapping(value = "/admin/book-register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerBook(@ModelAttribute @Valid BookCreateForm bookCreateForm) {
        List<BookAuthorDto> bookAuthors = new ArrayList<>();
        for (int i = 0; i < bookCreateForm.getAuthorNames().size(); i++) {
            String name = bookCreateForm.getAuthorNames().get(i);
            String role = bookCreateForm.getAuthorRoles().get(i);
            bookAuthors.add(new BookAuthorDto(name, role));
        }
        BookCreateRequest request = BookCreateRequest.from(bookCreateForm, bookAuthors);

        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            bookClient.createBook(jsonRequest, bookCreateForm.getCoverImage());

        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return "redirect:/admin";
        }

        return "redirect:/admin/book-register";
    }

    @GetMapping("/admin/book-edit/{book-id}")
    public String getBookEdit(@PathVariable(name = "book-id") Long bookId, Model model) {
        CommonResponse<BookDetailResponse> response = bookClient.getBookByIdWithParentCategory(bookId);
        List<CategoryTreeDto> categories = categoryService.getAllCategoriesTree();

        List<String> selectedTags = response.data().tags();
        List<BookAuthorDto> initialAuthors = response.data().authors();
        List<Long> selectedCategoryIds = response.data().categories().stream()
                .map(List::getLast)
                .map(CategoryFlatDto::id)
                .collect(Collectors.toList());

        model.addAttribute("book", response.data());
        model.addAttribute("categories", categories);
        model.addAttribute("selectedTags", selectedTags);
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

        List<BookAuthorDto> bookAuthors = new ArrayList<>();
        for (int i = 0; i < bookUpdateForm.getAuthorNames().size(); i++) {
            String name = bookUpdateForm.getAuthorNames().get(i);
            String role = bookUpdateForm.getAuthorRoles().get(i);
            bookAuthors.add(new BookAuthorDto(name, role));
        }
        BookUpdateRequest request = new BookUpdateRequest(bookUpdateForm, bookAuthors);

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

//    @GetMapping("/books/search-by-category")
//    public String getBooksByCategory(
//            @RequestParam("category") String categoryName,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            Model model
//    ) {
//        CommonResponse<PageResponse<BookResponse>> response = bookClient.getBooksByCategory(categoryName, page, size);
//        PageResponse<BookResponse> pageData = response.data();
//        List<BookResponse> books = pageData.content();
//
//        Map<Long, Integer> reviewCounts = new HashMap<>();
//        Map<Long, Double> averageRatings = new HashMap<>();
//
//        for (BookResponse book : books) {
//            Long bookId = book.id();
//            try {
//                PageResponse<ReviewListResponse> page0 = reviewClient.getReviewsByBookId(bookId, 0, 1);
//                int totalReviews = (int) page0.totalElements();
//                reviewCounts.put(bookId, totalReviews);
//
//                int totalPages = page0.totalPages();
//                List<ReviewListResponse> allReviews = new ArrayList<>();
//                for (int p = 0; p < totalPages; p++) {
//                    PageResponse<ReviewListResponse> reviewPage = reviewClient.getReviewsByBookId(bookId, p, 100);
//                    allReviews.addAll(reviewPage.content());
//                }
//
//                double avg = allReviews.stream()
//                        .mapToDouble(ReviewListResponse::rating)
//                        .average()
//                        .orElse(0.0);
//                averageRatings.put(bookId, avg);
//
//            } catch (Exception e) {
//                reviewCounts.put(bookId, 0);
//                averageRatings.put(bookId, 0.0);
//            }
//        }
//
//        model.addAttribute("bookList", books);
//        model.addAttribute("reviewCounts", reviewCounts);
//        model.addAttribute("averageRatings", averageRatings);
//
//        return "book/category-list";
//    }

    @GetMapping("/books/search")
    public String getSearchList() {
        return "book/search-list";
    }

    @GetMapping("/admin/list")
    public String showBookList(@RequestParam(defaultValue = "0") int page, Model model) {
        CommonResponse<PageResponse<AdminBookResponse>> response = bookClient.getBooksByAdmin(page, 10);
        PageResponse<AdminBookResponse> books = response.data();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                books.page(), books.totalPages(), 5
        );

        model.addAttribute("currentPage", "list");
        model.addAttribute("books", books);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/book/list";
    }

    @DeleteMapping("/admin/books/{bookId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookClient.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/books/{bookId}")
    public String issueCoupon(@PathVariable Long bookId,
                              @RequestParam Long couponId,
                              @RequestParam String originType,
                              @RequestParam Long originId,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model,
                              @CookieValue(name = "accessToken", required = false) String accessToken) {

        try {
            CouponIssueRequest req = new CouponIssueRequest(couponId, originType, originId);
            CommonResponse<StoresResponse> resp = couponStore.issueCoupon(req);
            model.addAttribute("successMessage", resp.data().couponName() + " 쿠폰이 발급되었습니다.");
        } catch (FeignException e) {
            String err = e.status() == 409
                    ? extractMessageFromFeignBody(e.contentUTF8())
                    : "중복 쿠폰 입니다.";
            model.addAttribute("errorMessage", err);
        }

        return getBookDetail(bookId, page, size, model, accessToken);
    }

    private String extractMessageFromFeignBody(String body) {
        try {
            var tree = new com.fasterxml.jackson.databind.ObjectMapper().readTree(body);
            return tree.path("message").asText();
        } catch (Exception ex) {
            return "알 수 없는 오류가 발생했습니다.";
        }
    }
}
