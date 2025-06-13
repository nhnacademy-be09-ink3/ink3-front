package com.nhnacademy.front.shop.guest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.common.dto.CommonResponse;
import com.nhnacademy.front.common.dto.PageResponse;
import com.nhnacademy.front.shop.book.dto.BookDetailResponse;
import com.nhnacademy.front.shop.cart.client.CartClient;
import com.nhnacademy.front.shop.cart.dto.CartBookResponse;
import com.nhnacademy.front.shop.cart.dto.GuestCartView;
import com.nhnacademy.front.shop.cart.dto.MeCartRequest;
import com.nhnacademy.front.shop.guest.client.GuestOrderClient;
import com.nhnacademy.front.shop.guest.dto.GuestOrderDetailsResponse;
import com.nhnacademy.front.shop.guest.dto.GuestOrderFormCreateRequest;
import com.nhnacademy.front.shop.guest.dto.GuestOrderResponse;
import com.nhnacademy.front.shop.order.dto.OrderBookResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GuestOrderService {
    private final CartClient cartClient;
    private final GuestOrderClient guestOrderClient;

    public GuestOrderDetailsResponse getGuestOrderDetails(long orderId, String email) {
        CommonResponse<GuestOrderDetailsResponse> guestOrderDetails = guestOrderClient.getGuestOrderDetails(orderId, email);
        return guestOrderDetails.data();
    }

    public PageResponse<OrderBookResponse> getOrderBookList(long orderId, Integer page, Integer size) {
        CommonResponse<PageResponse<OrderBookResponse>> orderBookListByOrderId = guestOrderClient.getOrderBookListByOrderId(orderId, page, size);
        return orderBookListByOrderId.data();
    }

    public GuestOrderResponse createGuestOrder(GuestOrderFormCreateRequest guestOrderFormCreateRequest) {
        CommonResponse<GuestOrderResponse> guestOrder = guestOrderClient.createGuestOrder(guestOrderFormCreateRequest);
        return guestOrder.data();
    }

    // 쿠키에서 비회원 장바구니 정보 가져오기.
    @NotNull
    public List<GuestCartView> getGuestCartViews(String guestCartCookie) {
        List<MeCartRequest> guestItems = new ArrayList<>();

        if(guestCartCookie != null && !guestCartCookie.isEmpty()){
            try{
                guestItems = parseGuestCookie(guestCartCookie);
            } catch (IOException e) {
                log.warn("비회원 장바구니 파싱 실패", e);
            }
        }

        List<GuestCartView> guestCartViews = new ArrayList<>();
        for(MeCartRequest item : guestItems){
            try {
                BookDetailResponse data = cartClient.getBookById(item.bookId()).data();
                CartBookResponse book =  new CartBookResponse(data.title(), data.originalPrice(), data.salePrice(),
                        data.discountRate(), data.thumbnailUrl(), data.isPackable());

                guestCartViews.add(new GuestCartView(item.bookId(), item.quantity(), book));
            }  catch (Exception e) {
                log.warn("도서 정보 조회 실패: {}", item.bookId(), e);
            }
        }
        return guestCartViews;
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
}
