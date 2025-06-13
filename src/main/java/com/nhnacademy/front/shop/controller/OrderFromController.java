package com.nhnacademy.front.shop.controller;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.address.client.dto.AddressResponse;
import com.nhnacademy.front.shop.address.service.AddressService;
import com.nhnacademy.front.shop.book.client.BookClient;
import com.nhnacademy.front.shop.book.dto.BookDetailResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartBookResponse;
import com.nhnacademy.front.shop.cart.dto.CartCouponResponse;
import com.nhnacademy.front.shop.cart.dto.GuestCartView;
import com.nhnacademy.front.shop.couponStore.dto.CouponStoreDto;
import com.nhnacademy.front.shop.couponStore.service.CouponStoreService;
import com.nhnacademy.front.shop.guest.service.GuestOrderService;
import com.nhnacademy.front.shop.order.dto.PackagingResponse;
import com.nhnacademy.front.shop.order.dto.ShippingPolicyResponse;
import com.nhnacademy.front.shop.order.service.OrderService;
import com.nhnacademy.front.shop.user.client.dto.UserResponse;
import com.nhnacademy.front.shop.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/order-form")
public class OrderFromController {
    private final GuestOrderService guestOrderService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final UserService userService;
    private final CartClient cartClient;
    private final BookClient bookClient;
    private final CouponStoreService couponStoreService;

    /**
     * 장바구니 -> 주문서 작성 페이지 return
     * @param model model
     * @return 주문서 작성 페이지 return
     */
    @GetMapping("/from-cart")
    public String getUserOrderFromCarts(
        @RequestParam(required = false) List<Long> cartIds,
        Model model,
        HttpServletRequest request,
        @CookieValue(value = "guest_cart", required = false) String guestCartCookie
    ) {
        System.out.println("📦 [Controller] cartIds = " + cartIds);
        addPackagingList(model);
        addShippingPolicy(model);

        if (orderService.isLoggedIn(request)) {
            addUserInfo(model);
            List<CartCouponResponse> cart = (cartIds != null && !cartIds.isEmpty())
                ? cartClient.getSelectedCartsWithCoupon(cartIds).data()
                : cartClient.getCartsWithCoupon().data();

            model.addAttribute("cart", cart);
            return "order/order-form-user-books";
        } else {
            List<GuestCartView> guestCartViews = guestOrderService.getGuestCartViews(guestCartCookie);
            model.addAttribute("cart", guestCartViews);
            return "order/order-form-guest-books";
        }
    }

    /**
     * 상품 -> 주문서 작성 페이지 return
     *
     * @param model    model
     * @param bookId   구매 상품Id
     * @param quantity 구매 수량
     * @param request  쿠키값에 accessToken여부 확인
     * @return 주문서 작성 페이지 return
     */
    @GetMapping("/from-book/{bookId}")
    public String getUserOrderFromBooks(
            Model model,
            @PathVariable long bookId,
            @RequestParam int quantity,
            HttpServletRequest request
    ) {
        addPackagingList(model);
        addShippingPolicy(model);

        if (orderService.isLoggedIn(request)) {
            long userId = addUserInfo(model);
            addBookInfoForUser(model, userId, bookId, quantity);
            return "order/order-form-user-books";
        } else {
            addBookInfoForGuest(model, bookId, quantity);
            return "order/order-form-guest-books";
        }
    }

    // 사용자 정보 입력
    private long addUserInfo(Model model) {
        UserResponse currentUser = userService.getCurrentUser();
        PageResponse<AddressResponse> addresses = addressService.getAddresses(0, 10);
        model.addAttribute("user", currentUser);
        model.addAttribute("addressList", addresses.content());
        return currentUser.id();
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

    // 상품 정보 (회원)
    private void addBookInfoForUser(Model model, long userId, long bookId, int quantity) {
        BookDetailResponse book = bookClient.getBookByIdWithParentCategory(bookId).data();
        List<CouponStoreDto> applicableCoupons = couponStoreService.getApplicableCoupons(userId, bookId);
        CartCouponResponse cartCouponResponse = new CartCouponResponse(
                0L,
                userId,
                book.id(),
                book.title(),
                book.originalPrice(),
                book.salePrice(),
                book.discountRate(),
                book.thumbnailUrl(),
                book.isPackable(),
                quantity,
                applicableCoupons);

        List<CartCouponResponse> cart = List.of(cartCouponResponse);
        model.addAttribute("cart", cart);
    }

    // 상품 정보 (비회원)
    private void addBookInfoForGuest(Model model, long bookId, int quantity) {
        BookDetailResponse book = bookClient.getBookByIdWithParentCategory(bookId).data();

        CartBookResponse cartBookResponse = new CartBookResponse(
                book.title(),
                book.originalPrice(),
                book.salePrice(),
                book.discountRate(),
                book.thumbnailUrl(),
                book.isPackable());

        GuestCartView guestCartView = new GuestCartView(
                bookId,
                quantity,
                cartBookResponse);

        List<GuestCartView> cart = List.of(guestCartView);
        model.addAttribute("cart", cart);
    }
}
