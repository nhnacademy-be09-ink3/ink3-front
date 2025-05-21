package com.nhnacademy.front.shop.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartRequest;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.user.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {
    private final UserClient userClient;
    private final CartClient cartClient;

    @PostMapping("/carts")
    public String addCartItem(@RequestParam Long bookId, @RequestParam int quantity) {
        Long userId = 2L;
        cartClient.addCart(new CartRequest(userId, bookId, quantity));
        return "redirect:/carts";
    }

    @PostMapping("/carts/update-quantity")
    @ResponseBody
    public void updateCartQuantity(@RequestParam Long cartId, @RequestParam int quantity) {
        cartClient.updateQuantity(cartId, new CartUpdateRequest(quantity));
    }

    // @GetMapping("/carts")
    // public String getCarts(Model model) {
    //     Long userId = 2L;
    //     List<CartResponse> carts = Collections.emptyList();
    //     try {
    //         carts = cartClient.getCarts(userId).getBody();
    //     } catch (Exception e) {
    //         log.error("장바구니 불러오기 실패", e);
    //     }
    //
    //     calculatePrice(model, Objects.requireNonNull(carts));
    //     return "cart/carts";
    // }
    @GetMapping("/carts")
    public String getCarts(Model model) {
        // Long userId;
        // try {
        //     // API 서버에서 userId 받아오기 (로그인 상태일 경우)
        //     CommonResponse<Map<String, Long>> response = userClient.getCurrentUser();
        //     userId = response.data().get("userId");
        // } catch (Exception e) {
        //     // 로그인 안 된 경우 (401 등)
        //     return "redirect:/guest-carts";
        // }

        // 로그인된 유저의 장바구니 조회
        List<CartResponse> carts = cartClient.getCarts(2L).getBody();
        calculatePrice(model, Objects.requireNonNull(carts));
        return "cart/carts";
    }

    // @GetMapping("/guest-carts")
    // public String getGuestCarts() {
    //     return "cart/guest-carts";
    // }

    @PostMapping("/carts/delete-all")
    public String deleteCarts() {
        Long userId = 2L;
        cartClient.deleteCarts(userId);
        return "redirect:/carts";
    }

    @PostMapping("/carts/delete-selected")
    public String deleteCart(@RequestParam List<Long> cartIds) {
        for (Long id : cartIds) {
            cartClient.deleteCart(id);
        }
        return "redirect:/carts";
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
