package com.nhnacademy.front.shop.book.tag.controller;

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
import com.nhnacademy.front.shop.controller.TagController;
import com.nhnacademy.front.shop.tag.client.TagClient;
import com.nhnacademy.front.shop.tag.client.dto.TagResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TagClient tagClient;

    @WithMockUser(roles = "ADMIN")
    @Test
    void getTagList() throws Exception {
        List<TagResponse> tags = List.of(
                new TagResponse(1L, "testTag1"),
                new TagResponse(2L, "testTag2")
        );

        PageResponse<TagResponse> pageResponse = new PageResponse<>(
                tags,
                0,
                10,
                2L,
                1,
                false,
                false
        );

        CommonResponse<PageResponse<TagResponse>> response = CommonResponse.success(pageResponse);

        given(tagClient.getTags(10, 0)).willReturn(response);

        mockMvc.perform(get("/admin/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/tags"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("pageInfo"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void createTag_redirectToTagList() throws Exception {
        CommonResponse<TagResponse> response = CommonResponse.success(new TagResponse(1L, "testTag1"));
        given(tagClient.createTag(any())).willReturn(response);

        mockMvc.perform(post("/admin/tags")
                        .param("name", "testTag1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tags"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void updateTag_redirectToTagList() throws Exception {
        CommonResponse<TagResponse> response = CommonResponse.success(new TagResponse(1L, "testTag1"));
        given(tagClient.updateTag(eq(1L), any())).willReturn(response);

        mockMvc.perform(put("/admin/tags/1")
                        .param("name", "testTag1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tags"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deleteTag_redirectToTagList() throws Exception {
        given(tagClient.deleteTag(1L)).willReturn(CommonResponse.success(null));

        mockMvc.perform(delete("/admin/tags/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tags"));
    }
}
