package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressResponse;
import com.nhnacademy.front.shop.address.service.AddressService;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderFromController {
    private final OrderService orderService;
    private final AddressService addressService;
    private final UserService userService;
    private final CartClient cartClient;

    /**
     * 주문서 작성 페이지 return
     * @param model
     * @return
     */
    @GetMapping("/user")
    public String getUserOrderFrom(Model model) {
        // 사용자 정보 입력
        UserResponse currentUser = userService.getCurrentUser();

        // 사용자 주소
        PageResponse<AddressResponse> addresses = addressService.getAddresses(0, 10);

        // 장바구니 리스트 or 도서 상품 1개 바로 가져오기
        CommonResponse<List<CartResponse>> cartResponse = cartClient.getCarts();
        List<CartResponse> cart = cartResponse.data();

        // 포장지 리스트 입력
        PageResponse<PackagingResponse> packagingList = orderService.getPackagingList(0, 100);

        model.addAttribute("cart", cart);
        model.addAttribute("user", currentUser);
        model.addAttribute("addressList", addresses.content());
        model.addAttribute("packagings", packagingList.content());
        return "order/order-form";
    }



    @GetMapping("/guest")
    public String getGuestOrderForm(Model model) {
        //TODO : 장바구니 리스트(쿠키) or 도서 상품 1개 바로 가져오기

        // 포장지 리스트 입력
        PageResponse<PackagingResponse> packagingList = orderService.getPackagingList(0, 100);

        model.addAttribute("packagings", packagingList.content());
        return "order/order-form";
    }
}
