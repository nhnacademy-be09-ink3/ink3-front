package com.nhnacademy.front.shop.user.client.dto;

public record UserStatisticsResponse(
        Long totalUsers,
        Long activeUsers,
        Long dormantUsers,
        Long withdrawnUsers
) {
}
