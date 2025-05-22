package com.nhnacademy.front.shop.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartResponse;
import com.nhnacademy.front.shop.cart.dto.CartUpdateRequest;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;

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
    public String getCarts(Model model) {
        CommonResponse<List<CartResponse>> carts = cartClient.getCarts();
        calculatePrice(model, Objects.requireNonNull(carts.data()));
        return "cart/carts";
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
