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
        if (request.isActive()) {
            membershipClient.activateMembership(request.id());
        } else {
            membershipClient.deactivateMembership(request.id());
        }

        return membershipClient.updateMembership(
                request.id(),
                new MembershipUpdateRequest(request.name(), request.conditionAmount(), request.pointRate())
        ).data();
    }

    public void setDefaultMembership(long id) {
        membershipClient.setDefaultMembership(id);
    }

    public void deleteMembership(long id) {
        membershipClient.deleteMembership(id);
    }
}
