package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.user.client.dto.UserListItemDto;
import com.nhnacademy.front.shop.user.service.UserService;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/users")
public class AdminUserManagementController {
    private final UserService userService;

    @GetMapping
    public String getUserManagement(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model
    ) {
        PageResponse<UserListItemDto> users = userService.getUsersForManagement(keyword, page, 10);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                users.page(), users.totalPages(), 10
        );
        model.addAttribute("users", users);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/user-management";
    }
}
