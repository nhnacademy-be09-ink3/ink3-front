package com.nhnacademy.front.shop.payment.client;
import com.nhnacademy.front.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shop-service")
public interface PaymentClient {
    // payment 결제 승인 요청
    // fix : return 값을 shop 서버랑 맞춰야 함
}
