package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatusUpdateRequest;
import com.nhnacademy.front.shop.order.dto.PackagingCreateRequest;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.PackagingUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundPolicyCreateRequest;
import com.nhnacademy.front.shop.order.dto.RefundPolicyResponse;
import com.nhnacademy.front.shop.order.dto.RefundPolicyUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyCreateRequest;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyUpdateRequest;
import java.time.LocalDateTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adminOrderClient", url = "${feign.url.shop}")
public interface AdminOrderClient {

    @GetMapping("/orders")
    CommonResponse<PageResponse<OrderResponse>> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PatchMapping("/orders/{orderId}/order-status")
    CommonResponse<Void> updateOrderStatus(@PathVariable long orderId,
                                           @RequestBody OrderStatusUpdateRequest request);




    @GetMapping("/shipping-policies")
    CommonResponse<PageResponse<ShippingPolicyResponse>> getShippingPolicyList(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/shipping-policies/activate")
    CommonResponse<ShippingPolicyResponse> getActivateShippingPolicy();

    @PostMapping("/shipping-policies")
    CommonResponse<ShippingPolicyResponse> createShippingPolicy(@RequestBody ShippingPolicyCreateRequest request);

    @PutMapping("/shipping-policies/{shippingPolicyId}")
    CommonResponse<ShippingPolicyResponse> updateShippingPolicy(
            @PathVariable long shippingPolicyId,
            @RequestBody ShippingPolicyUpdateRequest request);

    @PatchMapping("/shipping-policies/{shippingPolicyId}/activate")
    CommonResponse<Void> activateShippingPolicy(@PathVariable long shippingPolicyId);

    @DeleteMapping("/shipping-policies/{shippingPolicyId}")
    CommonResponse<Void> deleteShippingPolicy(@PathVariable long shippingPolicyId);

    @PatchMapping("/shipments/{orderId}/deliverea-at")
    CommonResponse<ShipmentResponse> updateShipmentDeliveredAt(
            @PathVariable("orderId") long orderId,
            @RequestParam LocalDateTime deliveredAt);






    @GetMapping("/packagings/activate")
    CommonResponse<PageResponse<PackagingResponse>> getActivatePackagings(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/packagings")
    CommonResponse<PageResponse<PackagingResponse>> getAllPackagings(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PostMapping("/packagings")
    CommonResponse<PackagingResponse> createPackagings(@RequestBody PackagingCreateRequest request);

    @PutMapping("/packagings/{packagingId}")
    CommonResponse<PackagingResponse> updatePackaging(
            @PathVariable("packagingId") long packagingId,
            @RequestBody PackagingUpdateRequest request);

    @PatchMapping("/packagings/{packagingId}/activate")
    CommonResponse<Void> activatePackaging(@PathVariable("packagingId") long packagingId);

    @PatchMapping("/packagings/{packagingId}/deactivate")
    CommonResponse<Void> deactivatePackaging(@PathVariable("packagingId") long packagingId);

    @DeleteMapping("/packagings/{packagingId}")
    CommonResponse<Void> deletePackaging(@PathVariable("packagingId") long packagingId);





    @GetMapping("/refunds")
    CommonResponse<PageResponse<RefundResponse>> getRefunds(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PostMapping("/refunds/{orderId}/users/{userId}")
    CommonResponse<Void> approveRefund(
            @PathVariable("orderId") long orderId,
            @PathVariable("userId") long userId);

    @PostMapping("/refunds/{orderId}")
    CommonResponse<Void> approveRefund(@PathVariable("orderId") String orderId);

    @GetMapping("/refund-policies/activate")
    CommonResponse<RefundPolicyResponse> getActivateRefundPolicy();

    @GetMapping("/refund-policies")
    CommonResponse<PageResponse<RefundPolicyResponse>> getRefundPolicyList(
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PostMapping("/refund-policies")
    CommonResponse<RefundPolicyResponse> createRefundPolicy(
            @RequestBody RefundPolicyCreateRequest request);

    @PutMapping("/refund-policies/{refundPolicyId}")
    CommonResponse<RefundPolicyResponse> updateRefundPolicy(
            @PathVariable long refundPolicyId,
            @RequestBody RefundPolicyUpdateRequest request);

    @PatchMapping("/refund-policies/{refundPolicyId}/activate")
    Void activateRefundPolicy(@PathVariable long refundPolicyId);

    @DeleteMapping("/refund-policies/{refundPolicyId}")
    Void deleteRefundPolicy(@PathVariable long refundPolicyId);
}
