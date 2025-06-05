package com.nhnacademy.front.shop.membership.service;

import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.membership.client.MembershipClient;
import com.nhnacademy.front.shop.membership.client.dto.MembershipCreateRequest;
import com.nhnacademy.front.shop.membership.client.dto.MembershipResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipStatisticsResponse;
import com.nhnacademy.front.shop.membership.client.dto.MembershipUpdateRequest;
import com.nhnacademy.front.shop.membership.dto.MembershipUpdateFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final MembershipClient membershipClient;

    public PageResponse<MembershipResponse> getMemberships(int page, int size, String sort) {
        return membershipClient.getMemberships(page, size, sort).data();
    }

    public MembershipStatisticsResponse getMembershipStatistics() {
        return membershipClient.getMembershipStatistics().data();
    }

    public MembershipResponse createMembership(MembershipCreateRequest request) {
        return membershipClient.createMembership(request).data();
    }

    public MembershipResponse updateMembership(MembershipUpdateFormRequest request) {
        return membershipClient.updateMembership(
                request.id(),
                new MembershipUpdateRequest(request.name(), request.conditionAmount(), request.pointRate())
        ).data();
    }

    public void activateMembership(long membershipId) {
        membershipClient.activateMembership(membershipId);
    }

    public void deactivateMembership(long membershipId) {
        membershipClient.deactivateMembership(membershipId);
    }

    public void setDefaultMembership(long membershipId) {
        membershipClient.setDefaultMembership(membershipId);
    }

    public void deleteMembership(long membershipId) {
        membershipClient.deleteMembership(membershipId);
    }
}
