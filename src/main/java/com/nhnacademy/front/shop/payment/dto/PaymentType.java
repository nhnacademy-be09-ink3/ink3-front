package com.nhnacademy.front.shop.payment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentType {
    TOSS, KAKAO_PAY, NAVER_PAY, POINT;

    @JsonCreator
    public static PaymentType from(String str){
        for(PaymentType type : PaymentType.values()){
            if (type.name().equals(str.toUpperCase())) {
                return type;
            }
        }
        //TODO : 어떤 에러를 만들어야하나.
        throw new IllegalArgumentException ();
    }
}
