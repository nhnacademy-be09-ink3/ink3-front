package com.nhnacademy.front.admin.controller;

import com.nhnacademy.front.admin.order.AdminOrderService;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.dto.OrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderStatus;
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
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner.Mode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin-order")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    // 관리자 주문 리스트 조회
    @GetMapping("/orders")
    public String orders(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<OrderResponse> orders = adminOrderService.getOrderList(page, size);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                orders.page(), orders.totalPages(), 10);
        log.info("order size = {}", orders.content().size());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orders", orders);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/order";
    }

    // 관리자 주문 상태 변경 요청
    @PostMapping("/orders/{orderId}/order-status")
    public String updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam("beforeStatus") String beforeStatus,
            @RequestParam("afterStatus") String afterStatus,
            RedirectAttributes redirectAttributes
    ) {
        try {
            adminOrderService.updateOrderStatus(orderId, beforeStatus, afterStatus);
            return "redirect:/admin-order/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "상태 변경 실패: " + e.getMessage());
            return "redirect:/admin-order/orders";
        }
    }







    @GetMapping("/shipping-policies")
    public String shipments(
            // 관리자 배송 정책 리스트 페이지
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<ShippingPolicyResponse> shippingPolicyList = adminOrderService.getShippingPolicyList(page, size);
        ShippingPolicyResponse activateShippingPolicy = adminOrderService.getActivateShippingPolicy();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                shippingPolicyList.page(), shippingPolicyList.totalPages(), 10);

        model.addAttribute("shippingPolicyList", shippingPolicyList);
        model.addAttribute("activateShippingPolicy", activateShippingPolicy);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/shipment-policy";
    }

    // 관리자 배송 정책 추가하기
    @PostMapping("/shipping-policies")
    public String createShippingPolicy(
            @ModelAttribute ShippingPolicyCreateRequest request
    ) {
        adminOrderService.createShippingPolicy(request);
        return "redirect:/admin-order/shipping-policies";
    }

    // 관리자 배송 정책 수정
    @PutMapping("/shipping-policies/{shippingPolicyId}")
    @ResponseBody
    public ResponseEntity<Void> updateShippingPolicy(
            @PathVariable long shippingPolicyId,
            @RequestBody ShippingPolicyUpdateRequest request
    ) {
        adminOrderService.updateShippingPolicy(shippingPolicyId, request);
        return ResponseEntity.ok().build();
    }

    // 관리자 배송 정책 삭제
    @DeleteMapping("/shipping-policies/{shippingPolicyId}")
    @ResponseBody
    public ResponseEntity<Void> deleteShippingPolicy(@PathVariable long shippingPolicyId) {
        adminOrderService.deleteShippingPolicy(shippingPolicyId);
        return ResponseEntity.ok().build();
    }

    // 관리자 배송 정책 활성화 하기 (기본 1개는 활성화 되어 있어야함)
    @PutMapping("/shipping-policies/{shippingPolicyId}/activate")
    @ResponseBody
    public ResponseEntity<Void> activateShippingPolicy(@PathVariable long shippingPolicyId) {
        adminOrderService.activateShippingPolicy(shippingPolicyId);
        return ResponseEntity.ok().build();
    }






    // 관리자 포장지 리스트 페이지
    @GetMapping("/packagings")
    public String getPackagings(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<PackagingResponse> activatePackagings = adminOrderService.getActivatePackagings(0, 50);
        PageResponse<PackagingResponse> allPackagings = adminOrderService.getAllPackagings(page, size);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                allPackagings.page(), allPackagings.totalPages(), 10);

        model.addAttribute("activatePackagings", activatePackagings);
        model.addAttribute("allPackagings", allPackagings);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/packaging-policy";
    }

    @PostMapping("/packagings")
    public String createPackagings(@ModelAttribute  PackagingCreateRequest packagingCreateRequest) {
        adminOrderService.createPackaging(packagingCreateRequest);
        return "redirect:/admin-order/packagings";
    }

    // 관리자 포장지 수정 요청
    @PutMapping("/packagings/{packagingId}")
    public String updatePackagings(
            Model model,
            @PathVariable Long packagingId,
            @ModelAttribute PackagingUpdateRequest request
            ) {
        adminOrderService.updatePackaging(packagingId, request);
        return "redirect:/admin-order/packagings";
    }

    // 관리자 포장지 활성화 요청
    @GetMapping("/packaings/{packagingId}/activate")
    public String activatePackagings(
            @PathVariable Long packagingId
    ) {
        adminOrderService.activatePackaging(packagingId);
        return "redirect:/admin-order/packagings";
    }

    // 관리자 포장지 비활성화 요청
    @GetMapping("/packaings/{packagingId}/deactivate")
    public String deactivatePackagings(
            @PathVariable Long packagingId
    ) {
        adminOrderService.deactivatePackaging(packagingId);
        return "redirect:/admin-order/packagings";
    }

    // 관리자 포장지 삭제 요청
    @GetMapping("/packagins/{packagingId}")
    public String deletePackagings(@PathVariable long packagingId){
        adminOrderService.deletePackaging(packagingId);
        return "redirect:/admin-order/packagings";
    }






    //TODO : 추가적으로 주문ID를 눌렀을 때 모달로 주문 상세 볼 수 있도록 하면 좋을듯
    // 관리자 반품 페이지
    @GetMapping("/refunds")
    public String refunds(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<RefundResponse> refunds = adminOrderService.getRefunds(page, size);
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                refunds.page(), refunds.totalPages(), 10);

        model.addAttribute("refunds", refunds);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/refund";
    }

    // 관리자 반품 승인 요청
    @PostMapping("/refunds/{orderId}")
    public String approveRefund(
            @PathVariable Long orderId,
            @RequestParam Long userId
    ) {
        adminOrderService.approveRefund(orderId, userId);
        return "redirect:/admin-order/refunds";
    }

    @GetMapping("/refund-policies")
    public String refundPolicies(
            // 관리자 반품 정책 리스트 페이지
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<RefundPolicyResponse> refundPolicyList = adminOrderService.getRefundPolicies(page, size);
        RefundPolicyResponse activateRefundPolicy = adminOrderService.getActivatedRefundPolicy();
        PageUtil.PageInfo pageInfo = PageUtil.calculatePageRange(
                refundPolicyList.page(), refundPolicyList.totalPages(), 10);

        model.addAttribute("refundPolicyList", refundPolicyList);
        model.addAttribute("activateRefundPolicy", activateRefundPolicy);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/order/refund-policy";
    }

    // 관리자 반품 정책 추가하기
    @PostMapping("/refund-policies")
    public String createRefundPolicy(
            @ModelAttribute RefundPolicyCreateRequest request
    ) {
        adminOrderService.createRefundPolicy(request);
        return "redirect:/admin-order/refund-policies";
    }

    // 관리자 반품 정책 수정
    @PutMapping("/refund-policies/{refundPolicyId}")
    @ResponseBody
    public ResponseEntity<Void> updateRefundPolicy(
            @PathVariable long refundPolicyId,
            @RequestBody RefundPolicyUpdateRequest request
    ) {
        adminOrderService.updateRefundPolicy(refundPolicyId, request);
        return ResponseEntity.ok().build();
    }

    // 관리자 반품 정책 삭제
    @DeleteMapping("/refund-policies/{refundPolicyId}")
    @ResponseBody
    public ResponseEntity<Void> deleteRefundPolicy(@PathVariable long refundPolicyId) {
        adminOrderService.deleteRefundPolicy(refundPolicyId);
        return ResponseEntity.ok().build();
    }

    // 관리자 반품 정책 활성화 하기
    @PutMapping("/refund-policies/{refundPolicyId}/activate")
    @ResponseBody
    public ResponseEntity<Void> activateRefundPolicy(@PathVariable long refundPolicyId) {
        adminOrderService.activateRefundPolicy(refundPolicyId);
        return ResponseEntity.ok().build();
    }
}
