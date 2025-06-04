package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adminOrderClient", url = "${feign.url.shop}")
public interface AdminOrderClient {

    @GetMapping("/refunds")
    CommonResponse<PageResponse<RefundResponse>> getRefunds(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PostMapping("/refunds/{orderId}")
    CommonResponse<Void> approveRefund(@PathVariable("orderId") long orderId);

/*
    @GetMapping("/refunds")
    CommonResponse<PageResponse<RefundResponse>> getRefunds(
            @RequestParam("page") int page,
            @RequestParam("size") int size);
*/

    @PostMapping("/refunds/{orderId}")
    CommonResponse<Void> approveRefund(@PathVariable("orderId") String orderId);
}
