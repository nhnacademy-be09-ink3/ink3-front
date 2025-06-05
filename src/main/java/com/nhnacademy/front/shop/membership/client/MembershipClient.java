package com.nhnacademy.front.shop.membership.client;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipCreateRequest;
import com.nhnacademy.front.shop.membership.client.dto.MembershipResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipStatisticsResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "membershipClient", url = "${feign.url.shop}/memberships")
public interface MembershipClient {
    @GetMapping
    CommonResponse<PageResponse<MembershipResponse>> getMemberships(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort
    );

    @GetMapping("/statistics")
    CommonResponse<MembershipStatisticsResponse> getMembershipStatistics();

    @PostMapping
    CommonResponse<MembershipResponse> createMembership(@RequestBody MembershipCreateRequest request);

    @PutMapping("/{membershipId}")
    CommonResponse<MembershipResponse> updateMembership(
            @PathVariable long membershipId,
            @RequestBody MembershipUpdateRequest request
    );

    @PatchMapping("/{membershipId}/activate")
    void activateMembership(@PathVariable long membershipId);

    @PatchMapping("/{membershipId}/deactivate")
    void deactivateMembership(@PathVariable long membershipId);

    @PatchMapping("/{membershipId}/default")
    void setDefaultMembership(@PathVariable long membershipId);

    @DeleteMapping("/{membershipId}")
    void deleteMembership(@PathVariable long membershipId);
}
