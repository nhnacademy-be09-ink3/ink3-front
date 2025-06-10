package com.nhnacademy.front.admin.order;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatus;
import com.nhnacademy.front.shop.order.dto.OrderStatusUpdateRequest;
import com.nhnacademy.front.shop.order.dto.PackagingCreateRequest;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.PackagingUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundCreateRequest;
import com.nhnacademy.front.shop.order.dto.RefundPolicyCreateRequest;
import com.nhnacademy.front.shop.order.dto.RefundPolicyResponse;
import com.nhnacademy.front.shop.order.dto.RefundPolicyUpdateRequest;
import com.nhnacademy.front.shop.order.dto.RefundResponse;
import com.nhnacademy.front.shop.order.dto.ShipmentResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyCreateRequest;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyUpdateRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.config.client.ConfigServerHealthIndicator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminOrderService {
    private final AdminOrderClient adminOrderClient;

    public PageResponse<OrderResponse> getOrderList(int page, int size){
        CommonResponse<PageResponse<OrderResponse>> orderResponse = adminOrderClient.getOrders(page, size);
        return orderResponse.data();
    }

    public void updateOrderStatus(long orderId, String beforeStatus, String afterStatus){
        OrderStatus before = OrderStatus.valueOf(beforeStatus);
        OrderStatus after = OrderStatus.valueOf(afterStatus);

        if(after==OrderStatus.DELIVERED){
            updateShipmentDeliveredAtToNow(orderId);
        }else if(before==OrderStatus.DELIVERED && ( after==OrderStatus.CREATED || after==OrderStatus.CONFIRMED)){
            updateShipmentDeliveredAtToNull(orderId);
        }
        adminOrderClient.updateOrderStatus(orderId, new OrderStatusUpdateRequest(after));
    }



    // 배송 정책 리스트
    public PageResponse<ShippingPolicyResponse> getShippingPolicyList(int page, int size) {
        return adminOrderClient.getShippingPolicyList(page, size).data();
    }
    // 배송 정책 활성화 리스트
    public ShippingPolicyResponse getActivateShippingPolicy() {
        return adminOrderClient.getActivateShippingPolicy().data();
    }
    // 배송 정책 생성
    public ShippingPolicyResponse createShippingPolicy(ShippingPolicyCreateRequest request) {
        return adminOrderClient.createShippingPolicy(request).data();
    }
    // 배송 정책 업데이트
    public ShippingPolicyResponse updateShippingPolicy(long shippingPolicyId, ShippingPolicyUpdateRequest request) {
        return adminOrderClient.updateShippingPolicy(shippingPolicyId, request).data();
    }
    // 배송 정책 삭제
    public void deleteShippingPolicy(long shippingPolicyId) {
        adminOrderClient.deleteShippingPolicy(shippingPolicyId);
    }
    // 배송 정책 활성화
    public void activateShippingPolicy(long shippingPolicyId) {
        adminOrderClient.activateShippingPolicy(shippingPolicyId);
    }
    // 배송 시간 변경 현재
    public ShipmentResponse updateShipmentDeliveredAtToNow(long orderId) {
        return adminOrderClient.updateShipmentDeliveredAt(orderId, LocalDateTime.now()).data();
    }
    // 배송 시간 변경 null
    public ShipmentResponse updateShipmentDeliveredAtToNull(long orderId) {
        return adminOrderClient.updateShipmentDeliveredAt(orderId, null).data();
    }



    // 활성 포장지 조회
    public PageResponse<PackagingResponse> getActivatePackagings(int page , int size) {
        return adminOrderClient.getActivatePackagings(page, size).data();
    }

    // 전체 포장지 조회
    public PageResponse<PackagingResponse> getAllPackagings(int page, int size) {
        return adminOrderClient.getAllPackagings(page, size).data();
    }

    // 포장지 생성
    public PackagingResponse createPackaging(PackagingCreateRequest request) {
        return adminOrderClient.createPackagings(request).data();
    }

    // 포장지 수정
    public PackagingResponse updatePackaging(long packagingId, PackagingUpdateRequest request) {
        return adminOrderClient.updatePackaging(packagingId, request).data();
    }

    // 포장지 활성화
    public void activatePackaging(long packagingId) {
        adminOrderClient.activatePackaging(packagingId);
    }

    // 포장지 비활성화
    public void deactivatePackaging(long packagingId) {
        adminOrderClient.deactivatePackaging(packagingId);
    }

    // 포장지 삭제
    public void deletePackaging(long packagingId) {
        adminOrderClient.deletePackaging(packagingId);
    }




    // 반품 리스트 조회
    public PageResponse<RefundResponse> getRefunds(int page, int size) {
        CommonResponse<PageResponse<RefundResponse>> refunds = adminOrderClient.getRefunds(page, size);
        return refunds.data();
    }
    // 반품 승인
    public void approveRefund(long orderId, long userId) {
        adminOrderClient.approveRefund(orderId , userId);
    }

    // 반품 정책 리스트 조회
    public PageResponse<RefundPolicyResponse> getRefundPolicies(int page, int size) {
        return adminOrderClient.getRefundPolicyList(page, size).data();
    }
    // 활성화 반품 정책 조회
    public RefundPolicyResponse getActivatedRefundPolicy() {
        return adminOrderClient.getActivateRefundPolicy().data();
    }
    // 반품 정책 생성
    public RefundPolicyResponse createRefundPolicy(RefundPolicyCreateRequest refundPolicyCreateRequest) {
        return adminOrderClient.createRefundPolicy(refundPolicyCreateRequest).data();
    }
    // 반품 정책 수정
    public RefundPolicyResponse updateRefundPolicy(long refundPolicyId, RefundPolicyUpdateRequest refundPolicyUpdateRequest) {
        return adminOrderClient.updateRefundPolicy(refundPolicyId, refundPolicyUpdateRequest).data();
    }
    // 반품 정책 삭제
    public void deleteRefundPolicy(long refundPolicyId) {
        adminOrderClient.deleteRefundPolicy(refundPolicyId);
    }
    // 반품 정책 활성화
    public void activateRefundPolicy(long refundPolicyId) {
        adminOrderClient.activateRefundPolicy(refundPolicyId);
    }
}
