package com.nhnacademy.front.shop.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartClient cartClient;

    @PostMapping("/carts")
    public String addCartItem(@RequestParam Long bookId, @RequestParam int quantity) {
        cartClient.addCart(new MeCartRequest(bookId, quantity));
        return "redirect:/carts";
    }

    @PostMapping("/carts/update-quantity")
    @ResponseBody
    public void updateCartQuantity(@RequestParam Long cartId, @RequestParam int quantity) {
        cartClient.updateQuantity(cartId, new CartUpdateRequest(quantity));
    }

    @GetMapping("/carts")
    public String getCarts(
        @CookieValue(value = "guest_cart", required = false, defaultValue = "EMPTY") String guestCartCookie,
        HttpServletResponse response, Model model
    ) {
        if (!"EMPTY".equals(guestCartCookie)) {
            try {
                List<MeCartRequest> guestItems = parseGuestCookie(guestCartCookie);
                if (!guestItems.isEmpty()) {
                    log.info("🟢 병합 요청 전송: {}건", guestItems.size());
                    cartClient.mergeGuestCart(guestItems);

                    ResponseCookie deleteCookie = ResponseCookie.from("guest_cart", "")
                        .path("/")
                        .maxAge(0)
                        .build();
                    response.addHeader("Set-Cookie", deleteCookie.toString());
                }
            } catch (Exception e) {
                log.warn("⚠️ 비회원 장바구니 병합 실패", e);
            }
        }

        CommonResponse<List<CartResponse>> carts = cartClient.getCarts();
        calculatePrice(model, Objects.requireNonNull(carts.data()));
        return "cart/carts";
    }

    @GetMapping("/guest-carts")
    public String getGuestCarts() {
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
