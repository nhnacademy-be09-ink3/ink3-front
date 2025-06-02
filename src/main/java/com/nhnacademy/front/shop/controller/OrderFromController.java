package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressResponse;
import com.nhnacademy.front.shop.address.service.AddressService;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//TODO : 쿠폰 적용 (회원)
@RequiredArgsConstructor
@Controller
@RequestMapping("/order-form")
public class OrderFromController {
    private final OrderService orderService;
    private final AddressService addressService;
    private final UserService userService;
    private final CartClient cartClient;
    private final BookClient bookClient;

    /**
     * 주문서 작성 페이지 return
     * @param model
     * @return
     */
    @GetMapping("/from-cart")
    public String getUserOrderFromCarts(Model model, HttpServletRequest request) {
        addPackagingList(model);
        addShippingPolicy(model);

        if(orderService.isLoggedIn(request)){
            addUserInfo(model);
            // 장바구니 리스트
            CommonResponse<List<CartResponse>> cartResponse = cartClient.getCarts();
            List<CartResponse> cart = cartResponse.data();
            model.addAttribute("cart", cart);
            return "order/order-form-user-books";
        }else {
            //TODO 쿠키값에서 꺼내서 사용해야함. (임시 데이터)
            List<CartResponse> testCart = List.of(
                    new CartResponse(
                            1L,             // cartId
                            null,           // userId (비회원이므로 null)
                            101L,           // bookId
                            "자바의 정석",     // bookTitle
                            30000,          // originalBookPrice
                            27000,          // saleBookPrice
                            10,            // bookDiscountRate
                            "/images/java.jpg", // thumbnailUrl
                            1              // quantity
                    ));
            model.addAttribute("cart", testCart);
            return "order/order-form-guest-books";
        }
    }

    @GetMapping("/from-book/{bookId}")
    public String getUserOrderFromBooks(
            Model model,
            @PathVariable long bookId,
            @RequestParam int quantity,
            HttpServletRequest request
    ) {
        addBookInfo(model, bookId, quantity);
        addPackagingList(model);
        addShippingPolicy(model);

        if(orderService.isLoggedIn(request)) {
            addUserInfo(model);
            return "order/order-form-user-book";
        }else{
            return "order/order-form-guest-book";
        }
    }



    // 사용자 정보 입력
    private void addUserInfo(Model model) {
        UserResponse currentUser = userService.getCurrentUser();
        PageResponse<AddressResponse> addresses = addressService.getAddresses(0, 10);
        model.addAttribute("user", currentUser);
        model.addAttribute("addressList", addresses.content());
    }

    // 상품 정보
    private void addBookInfo(Model model, long bookId, int quantity) {
        CommonResponse<BookResponse> bookDetailResponse = bookClient.getBookDetail(bookId);
        model.addAttribute("book", bookDetailResponse.data());
        model.addAttribute("quantity", quantity);
    }

    // 포장지 정보
    private void addPackagingList(Model model) {
        PageResponse<PackagingResponse> packagingList = orderService.getPackagingList(0, 100);
        model.addAttribute("packagings", packagingList.content());
    }

    // 배송 정책 가져오기
    private void addShippingPolicy(Model model) {
        ShippingPolicyResponse activeShippingPolicy = orderService.getActiveShippingPolicy();
        model.addAttribute("activeShippingPolicy", activeShippingPolicy);
    }
}
