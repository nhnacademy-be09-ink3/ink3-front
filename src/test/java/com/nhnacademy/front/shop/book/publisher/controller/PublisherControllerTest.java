package com.nhnacademy.front.shop.book.publisher.controller;

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
import com.nhnacademy.front.shop.controller.PublisherController;
import com.nhnacademy.front.shop.publisher.client.PublisherClient;
import com.nhnacademy.front.shop.publisher.client.dto.PublisherResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherClient publisherClient;

    @WithMockUser(roles = "ADMIN")
    @Test
    void getPublisherList() throws Exception {
        List<PublisherResponse> publishers = List.of(
                new PublisherResponse(1L, "testPub1"),
                new PublisherResponse(2L, "testPub2")
        );

        PageResponse<PublisherResponse> pageResponse = new PageResponse<>(
                publishers,
                0,
                10,
                2L,
                1,
                false,
                false
        );

        CommonResponse<PageResponse<PublisherResponse>> response = CommonResponse.success(pageResponse);

        given(publisherClient.getPublishers(10, 0)).willReturn(response);

        mockMvc.perform(get("/admin/pubs"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/publishers"))
                .andExpect(model().attributeExists("publishers"))
                .andExpect(model().attributeExists("pageInfo"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void createPublisher_redirectToPublisherList() throws Exception {
        CommonResponse<PublisherResponse> response = CommonResponse.success(new PublisherResponse(1L, "testPub1"));
        given(publisherClient.createPublisher(any())).willReturn(response);

        mockMvc.perform(post("/admin/pubs")
                        .param("name", "testPub1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pubs"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void updatePublisher_redirectToPublisherList() throws Exception {
        CommonResponse<PublisherResponse> response = CommonResponse.success(new PublisherResponse(1L, "testPub1"));
        given(publisherClient.updatePublisher(eq(1L), any())).willReturn(response);

        mockMvc.perform(put("/admin/pubs/1")
                        .param("name", "testPub1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pubs"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deletePublisher_redirectToPublisherList() throws Exception {
        given(publisherClient.deletePublisher(1L)).willReturn(CommonResponse.success(null));

        mockMvc.perform(delete("/admin/pubs/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pubs"));
    }
}
