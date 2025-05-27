package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.예시DTO.AddressResponse;
import com.nhnacademy.front.shop.order.예시DTO.CartResponse;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
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
    private final OrderClient orderClient;

    /**
     * 주문서 작성 페이지 return
     * @param model
     * @return
     */
    @GetMapping("/user")
    public String getUserOrderFrom(Model model, HttpServletResponse request) {
        //TODO : me 방식으로 바꿔야함!
        // Long userId = Long.parseLong(request.getHeader("X-USER-ID"));

        // 사용자 주소
        CommonResponse<PageResponse<AddressResponse>> userAddresses = orderClient.getUserAddresses(4L,0,10);
        List<AddressResponse> addressList = userAddresses.data().content();

        // 사용자 정보 입력 -> get me 방식으로 수정.
        CommonResponse<UserResponse> currentUser = orderClient.getUser(4L);
        UserResponse user = currentUser.data();

        // 장바구니 리스트 or 도서 상품 1개 바로 가져오기
        CommonResponse<List<CartResponse>> cartsResponse = orderClient.getCartsWithCoupons(4L);
        List<CartResponse> cart = cartsResponse.data();


        // 포장지 리스트 입력
        CommonResponse<PageResponse<PackagingResponse>> packagingsResponse = orderClient.getPackagings(0,100);
        PageResponse<PackagingResponse> packagingsPageResponse = packagingsResponse.data();
        List<PackagingResponse> packagings = packagingsPageResponse.content();

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("addressList", addressList);
        model.addAttribute("packagings", packagings);
        return "/order/orderPage";
    }



    @GetMapping("/guest")
    public String getGuestOrderForm(Model model) {
        //TODO : 장바구니 리스트(쿠키) or 도서 상품 1개 바로 가져오기


        // 포장지 리스트 입력
        CommonResponse<PageResponse<PackagingResponse>> packagingsResponse = orderClient.getPackagings(0,100);
        PageResponse<PackagingResponse> packagingsPageResponse = packagingsResponse.data();
        List<PackagingResponse> packagings = packagingsPageResponse.content();


        model.addAttribute("packagings", packagings);
        return "/order/orderPage";
    }
}
