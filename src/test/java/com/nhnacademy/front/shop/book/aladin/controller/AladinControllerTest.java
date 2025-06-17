package com.nhnacademy.front.shop.book.aladin.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.client.AladinClient;
import com.nhnacademy.front.shop.book.dto.AladinBookResponse;
import com.nhnacademy.front.shop.book.dto.BookRegisterRequest;
import com.nhnacademy.front.shop.book.dto.BookStatus;
import com.nhnacademy.front.shop.controller.AladinController;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(AladinController.class)
public class AladinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AladinClient aladinClient;

    @WithMockUser(roles = "ADMIN")
    @Test
    void getBooksByAladin_withoutKeyword_null() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/aladin/book-search"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/aladin-search"))
                .andExpect(model().attribute("keyword", nullValue()))
                .andExpect(model().attributeExists("aladinBooks", "pageInfo", "currentPage"))
                .andReturn();

        PageResponse<?> aladinBooks = (PageResponse<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("aladinBooks");
        Assertions.assertThat(aladinBooks.content()).isEmpty();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getBooksByAladin_withoutKeyword_blank() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/aladin/book-search")
                        .param("keyword", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/aladin-search"))
                .andExpect(model().attributeExists("aladinBooks", "pageInfo", "currentPage"))
                .andReturn();

        Mockito.verify(aladinClient, Mockito.never())
                .getBooksByKeyword(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        PageResponse<?> aladinBooks = (PageResponse<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("aladinBooks");
        Assertions.assertThat(aladinBooks.content()).isEmpty();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getBooksByAladin_withKeyword() throws Exception {
        String keyword = "test";
        int page = 0;

        List<AladinBookResponse> books = List.of(
                new AladinBookResponse("title1", "description1", "author1", "publisher1",
                        "2025-05-15", "isbn1", 20000, "image1", "category1"),
                new AladinBookResponse("title2", "description1", "author2", "publisher2",
                        "2025-06-15", "isbn2", 10000, "image2", "category2")
        );

        PageResponse<AladinBookResponse> pageResponse = new PageResponse<>(
                books, page, 10, 2L, 1, false, false
        );
        CommonResponse<PageResponse<AladinBookResponse>> response = CommonResponse.success(pageResponse);

        given(aladinClient.getBooksByKeyword(eq(keyword), eq(page), eq(10))).willReturn(response);

        MvcResult result = mockMvc.perform(get("/admin/aladin/book-search")
                        .param("keyword", keyword)
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/aladin-search"))
                .andExpect(model().attribute("keyword", keyword))
                .andExpect(model().attributeExists("aladinBooks", "pageInfo", "currentPage"))
                .andReturn();

        PageResponse<?> aladinBooks = (PageResponse<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("aladinBooks");
        Assertions.assertThat(aladinBooks.content()).hasSize(2);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void registerBookByAladin() throws Exception {

        AladinBookResponse aladinBookResponse = new AladinBookResponse("title1", "description1", "author1", "publisher1",
                "2025-05-15", "isbn1", 20000, "image1", "category1");

        mockMvc.perform(post("/admin/aladin/book-register")
                        .param("aladinBookResponse.title", aladinBookResponse.title())
                        .param("aladinBookResponse.description", aladinBookResponse.description())
                        .param("aladinBookResponse.author", aladinBookResponse.author())
                        .param("aladinBookResponse.publisher", aladinBookResponse.publisher())
                        .param("aladinBookResponse.pubDate", aladinBookResponse.pubDate())
                        .param("aladinBookResponse.isbn13", aladinBookResponse.isbn13())
                        .param("aladinBookResponse.priceStandard", String.valueOf(aladinBookResponse.priceStandard()))
                        .param("aladinBookResponse.cover", aladinBookResponse.cover())
                        .param("aladinBookResponse.categoryName", aladinBookResponse.categoryName())
                        .param("contents", "contents1")
                        .param("priceSales", String.valueOf(18000))
                        .param("quantity", String.valueOf(100))
                        .param("status", BookStatus.AVAILABLE.name())
                        .param("isPackable", String.valueOf(true))
                        .param("tags", "tag1", "tag2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/aladin/book-search"));

        verify(aladinClient).registerBook(any(BookRegisterRequest.class));
    }
}
