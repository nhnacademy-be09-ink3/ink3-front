package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.order.client.OrderClient;
import com.nhnacademy.front.shop.order.예시DTO.AddressResponse;
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
     * 1. model : 사용자 주소
     * @param model
     * @return
     */
    @GetMapping
    public String getOrderFrom(Model model){
        // 사용자 주소
/*
        CommonResponse<PageResponse<AddressResponse>> userAddresses = orderClient.getUserAddresses();
        List<AddressResponse> addressList = userAddresses.data().content();
        model.addAttribute("addressList", addressList);
*/

        // 주문 상품 리스트

        // 사용자 정보 입력

        // 쿠폰 리스트 입력

        // 포인트 정보 입력



        //TODO : 예시 데이터이기 때문에 삭제 필요.
        List<AddressResponse> addressListTest = List.of(
                new AddressResponse(
                        1L,
                        "홍길동",
                        "12345",
                        "서울특별시 강남구 테헤란로 123",
                        "101동 202호",
                        "삼성역 근처",
                        true
                ),
                new AddressResponse(
                        2L,
                        "김영희",
                        "54321",
                        "부산광역시 해운대구 우동 456",
                        "301호",
                        "",
                        false
                )
        );

        model.addAttribute("addressList", addressListTest);
        return "/order/orderPage";
    }
}
