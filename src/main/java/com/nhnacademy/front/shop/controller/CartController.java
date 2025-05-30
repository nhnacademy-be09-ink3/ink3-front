package com.nhnacademy.front.shop.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.book.client.dto.BookResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartBookResponse;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.cart.dto.GuestCartView;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartClient cartClient;

    /**
     * 장바구니 추가
     * @param bookId
     * @param quantity
     * @return
     */
    @PostMapping("/carts")
    public String addCartItem(@RequestParam Long bookId, @RequestParam int quantity) {
        cartClient.addCart(new MeCartRequest(bookId, quantity));
        return "redirect:/carts";
    }

    /**
     * 비회원 장바구니 추가
     * @param bookId
     * @param quantity
     * @param guestCartCookie
     * @param response
     * @return
     */
    @PostMapping("/guest-carts")
    public String addGuestCartItem(
        @RequestParam Long bookId,
        @RequestParam int quantity,
        @CookieValue(value = "guest_cart", required = false) String guestCartCookie,
        HttpServletResponse response
    ) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            List<MeCartRequest> cartList = new ArrayList<>();

            if (guestCartCookie != null && !guestCartCookie.isEmpty()) {
                cartList = mapper.readValue(
                    URLDecoder.decode(guestCartCookie, StandardCharsets.UTF_8),
                    new TypeReference<>() {}
                );
            }

            boolean exists = false;
            for (int i = 0; i < cartList.size(); i++) {
                MeCartRequest item = cartList.get(i);
                if (item.bookId().equals(bookId)) {
                    int newQuantity = item.quantity() + quantity;
                    cartList.set(i, new MeCartRequest(bookId, newQuantity));
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                cartList.add(new MeCartRequest(bookId, quantity));
            }

            String encoded = URLEncoder.encode(mapper.writeValueAsString(cartList), StandardCharsets.UTF_8);
            ResponseCookie cookie = ResponseCookie.from("guest_cart", encoded)
                .path("/")
                .maxAge(60 * 60 * 24 * 3)
                .build();
            response.addHeader("Set-Cookie", cookie.toString());
            log.info("비회원 장바구니 저장 완료");

        } catch (IOException e) {
            log.error("비회원 장바구니 저장 실패", e);
        }

        return "redirect:/guest-carts";
    }

    /**
     * 수량 변경
     * @param cartId
     * @param quantity
     */
    @PostMapping("/carts/update-quantity")
    @ResponseBody
    public void updateCartQuantity(@RequestParam Long cartId, @RequestParam int quantity) {
        cartClient.updateQuantity(cartId, new CartUpdateRequest(quantity));
    }

    /**
     * 장바구니 조회
     * @param guestCartCookie
     * @param response
     * @param model
     * @return
     */
    @GetMapping("/carts")
    public String getCarts(
        @CookieValue(value = "guest_cart", required = false, defaultValue = "EMPTY") String guestCartCookie,
        HttpServletResponse response, Model model
    ) {
        if (!"EMPTY".equals(guestCartCookie)) {
            try {
                List<MeCartRequest> guestItems = parseGuestCookie(guestCartCookie);
                if (!guestItems.isEmpty()) {
                    log.info("병합 요청 전송: {}건", guestItems.size());
                    cartClient.mergeGuestCart(guestItems);

                    deleteCookie(response);
                }
            } catch (Exception e) {
                log.warn("비회원 장바구니 병합 실패", e);
            }
        }

        CommonResponse<List<CartResponse>> carts = cartClient.getCarts();
        calculatePrice(model, Objects.requireNonNull(carts.data()));
        return "cart/carts";
    }

    /**
     * 비회원 장바구니 조회
     * @param guestCartCookie
     * @param model
     * @return
     */
    @GetMapping("/guest-carts")
    public String getGuestCarts(
        @CookieValue(value = "guest_cart", required = false) String guestCartCookie,
        Model model
    ) {
        List<MeCartRequest> guestItems = new ArrayList<>();

        if (guestCartCookie != null && !guestCartCookie.isEmpty()) {
            try {
                guestItems = parseGuestCookie(guestCartCookie);
            } catch (IOException e) {
                log.warn("비회원 장바구니 파싱 실패", e);
            }
        }

        List<GuestCartView> guestCartViews = new ArrayList<>();
        int orderPrices = 0;
        int discountPrices = 0;

        for (MeCartRequest item : guestItems) {
            try {
                BookResponse data = cartClient.getBookById(item.bookId()).data();
                CartBookResponse book = new CartBookResponse(data.title(), data.originalPrice(), data.salePrice(),
                    data.discountRate(), data.thumbnailUrl());
                log.info("🔍 응답 book = {}", book);
                int quantity = item.quantity();
                int order = book.originalBookPrice() * quantity;
                int discount = (book.originalBookPrice() - book.saleBookPrice()) * quantity;

                guestCartViews.add(new GuestCartView(item.bookId(), quantity, book));
                orderPrices += order;
                discountPrices += discount;
            } catch (Exception e) {
                log.warn("도서 정보 조회 실패: {}", item.bookId(), e);
            }
        }

        model.addAttribute("guestCarts", guestCartViews);
        model.addAttribute("orderPrices", orderPrices);
        model.addAttribute("discountPrices", discountPrices);
        model.addAttribute("totalPrice", orderPrices - discountPrices);
        return "cart/guest-carts";
    }


    @PostMapping("/carts/delete-all")
    public String deleteCarts() {
        cartClient.deleteCarts();
        return "redirect:/carts";
    }

    @PostMapping("/carts/delete-selected")
    public String deleteCart(@RequestParam List<Long> cartIds) {
        for (Long id : cartIds) {
            cartClient.deleteCart(id);
        }
        return "redirect:/carts";
    }

    private List<MeCartRequest> parseGuestCookie(String cookie) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(
            URLDecoder.decode(cookie, StandardCharsets.UTF_8),
            new TypeReference<>() {
            }
        );
    }

    private void deleteCookie(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("guest_cart", "")
            .path("/")
            .maxAge(0)
            .build();
        response.addHeader("Set-Cookie", deleteCookie.toString());
    }

    private void calculatePrice(Model model, List<CartResponse> carts) {
        int orderPrices = 0;
        int discountPrices = 0;
        for (CartResponse cart : carts) {
            orderPrices += cart.originalBookPrice() * cart.quantity();
            discountPrices += (cart.originalBookPrice() - cart.saleBookPrice()) * cart.quantity();
        }
        int totalPrice = orderPrices - discountPrices;

        model.addAttribute("carts", carts);
        model.addAttribute("orderPrices", orderPrices);
        model.addAttribute("discountPrices", discountPrices);
        model.addAttribute("totalPrice", totalPrice);
    }
}
