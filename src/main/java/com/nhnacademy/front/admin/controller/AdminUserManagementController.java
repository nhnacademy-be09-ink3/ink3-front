package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipCreateRequest;
import com.nhnacademy.front.shop.membership.client.dto.MembershipResponse;
import com.nhnacademy.front.shop.membership.dto.MembershipUpdateFormRequest;
import com.nhnacademy.front.shop.membership.service.MembershipService;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyCreateRequest;
import com.nhnacademy.front.shop.point.policy.client.dto.PointPolicyResponse;
import com.nhnacademy.front.shop.point.policy.dto.PointPolicyUpdateFormRequest;
import com.nhnacademy.front.shop.point.policy.service.PointPolicyService;
import com.nhnacademy.front.shop.user.client.dto.UserListItemDto;
import com.nhnacademy.front.shop.user.service.UserService;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminUserManagementController {
    private final UserService userService;
    private final MembershipService membershipService;
    private final PointPolicyService pointPolicyService;

    @GetMapping("/user")
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

    @GetMapping("/membership")
    public String getMembershipManagement(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model
    ) {
        PageResponse<MembershipResponse> memberships = membershipService.getMemberships(page, 10, null);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                memberships.page(), memberships.totalPages(), 5
        );
        model.addAttribute("memberships", memberships);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("statistics", membershipService.getMembershipStatistics());
        return "admin/user/membership-management";
    }

    @PostMapping("/membership/create")
    public String createMembership(@ModelAttribute MembershipCreateRequest request) {
        membershipService.createMembership(request);
        return "redirect:/admin/membership";
    }

    @PostMapping("/membership/update")
    public String updateMembership(@ModelAttribute MembershipUpdateFormRequest request) {
        membershipService.updateMembership(request);
        return "redirect:/admin/membership";
    }

    @PostMapping("/membership/default")
    public String setDefaultMembership(@RequestParam long id) {
        membershipService.setDefaultMembership(id);
        return "redirect:/admin/membership";
    }

    @PostMapping("/membership/delete")
    public String deleteMembership(@RequestParam long id) {
        membershipService.deleteMembership(id);
        return "redirect:/admin/membership";
    }

    @GetMapping("/point-policy")
    public String getPointPolicyManagement(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model
    ) {
        PageResponse<PointPolicyResponse> pointPolicies = pointPolicyService.getPointPolicies(page, 10, null);
        log.info("pointPolicies: {}", pointPolicies);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                pointPolicies.page(), pointPolicies.totalPages(), 5
        );
        model.addAttribute("pointPolicies", pointPolicies);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("statistics", pointPolicyService.getPointPolicyStatistics());
        return "admin/user/point-policy-management";
    }

    @PostMapping("/point-policy/create")
    public String createPointPolicy(@ModelAttribute PointPolicyCreateRequest request) {
        pointPolicyService.createPointPolicy(request);
        return "redirect:/admin/point-policy";
    }

    @PostMapping("/point-policy/update")
    public String updatePointPolicy(@ModelAttribute PointPolicyUpdateFormRequest request) {
        pointPolicyService.updatePointPolicy(request);
        return "redirect:/admin/point-policy";
    }

    @PostMapping("/point-policy/activate")
    public String activatePointPolicy(@RequestParam long id) {
        pointPolicyService.activatePointPolicy(id);
        return "redirect:/admin/point-policy";
    }

    @PostMapping("/point-policy/delete")
    public String deletePointPolicy(@RequestParam long id) {
        pointPolicyService.deletePointPolicy(id);
        return "redirect:/admin/point-policy";
    }
}
