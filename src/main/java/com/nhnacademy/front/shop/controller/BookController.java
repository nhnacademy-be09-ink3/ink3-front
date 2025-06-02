package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.author.client.AuthorClient;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookCreateRequest;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import com.nhnacademy.front.shop.category.client.CategoryClient;
import com.nhnacademy.front.shop.category.client.dto.CategoryResponse;
import com.nhnacademy.front.shop.publisher.client.PublisherClient;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import com.nhnacademy.front.shop.tag.client.TagClient;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookClient bookClient;
    private final CategoryClient categoryClient;
    private final AuthorClient authorClient;
    private final PublisherClient publisherClient;
    private final TagClient tagClient;

    @GetMapping("/books/{bookId}")
    public String getBookDetail(@PathVariable Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        model.addAttribute("book", response.data());
        return "book/book-detail";
    }

    @GetMapping("/book-edit/{book-id}")
    public String getBookEdit(@PathVariable(name="book-id") Long bookId, Model model) {
        CommonResponse<BookResponse> response = bookClient.getBookDetail(bookId);
        CommonResponse<PageResponse<AuthorResponse>> authorList = authorClient.getAuthors(5, 0);
        CommonResponse<PageResponse<PublisherResponse>> publisherList = publisherClient.getPublishers(5, 0);
        CommonResponse<PageResponse<CategoryResponse>> categoryList = categoryClient.getCategories();
        CommonResponse<PageResponse<TagResponse>> tagList = tagClient.getTags(5, 0);

        model.addAttribute("book", response.data());
        model.addAttribute("authors", authorList.data().content());
        model.addAttribute("publishers", publisherList.data().content());
        model.addAttribute("categories", categoryList.data().content());
        model.addAttribute("tags", tagList.data().content());

        return "book/book-edit";
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

        return "admin/book-register";
    }

    @PostMapping("/admin/book-register")
    public String registerBook(@ModelAttribute BookCreateRequest request) {
        bookClient.createBook(request);
        return "redirect:/admin/book-register";
    }

    @GetMapping("/books/category")
    public String getBooksByCategory(@RequestParam String name, Model model) {
        model.addAttribute("categoryName", name);
        return "book/category-list";
    }

    @GetMapping("/books/search")
    public String getSearchList() {
        return "book/search-list";
    }
}
