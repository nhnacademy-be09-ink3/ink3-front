package com.nhnacademy.front.shop.book.author.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.author.client.AuthorClient;
import com.nhnacademy.front.shop.author.client.dto.AuthorResponse;
import com.nhnacademy.front.shop.controller.AuthorController;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorClient authorClient;

    @WithMockUser(roles = "ADMIN")
    @Test
    void getAuthorList() throws Exception {
        List<AuthorResponse> authors = List.of(
                new AuthorResponse(1L, "testAuthor1"),
                new AuthorResponse(2L, "testAuthor2")
        );

        PageResponse<AuthorResponse> pageResponse = new PageResponse<>(
                authors,
                0,
                10,
                2L,
                1,
                false,
                false
        );

        CommonResponse<PageResponse<AuthorResponse>> response = CommonResponse.success(pageResponse);

        given(authorClient.getAuthors(10, 0)).willReturn(response);

        mockMvc.perform(get("/admin/authors"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/authors"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("pageInfo"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void createAuthor_redirectToAuthorList() throws Exception {
        CommonResponse<AuthorResponse> response = CommonResponse.success(new AuthorResponse(1L, "testAuthor1"));
        given(authorClient.createAuthor(any())).willReturn(response);

        mockMvc.perform(post("/admin/authors")
                        .param("name", "testAuthor1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/authors"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void updateAuthor_redirectToAuthorList() throws Exception {
        CommonResponse<AuthorResponse> response = CommonResponse.success(new AuthorResponse(1L, "testAuthor1"));
        given(authorClient.updateAuthor(eq(1L), any())).willReturn(response);

        mockMvc.perform(put("/admin/authors/1")
                        .param("name", "testAuthor1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/authors"));

    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deleteAuthor_redirectToAuthorList() throws Exception {
        given(authorClient.deleteAuthor(1L)).willReturn(CommonResponse.success(null));

        mockMvc.perform(delete("/admin/authors/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/authors"));
    }
}
