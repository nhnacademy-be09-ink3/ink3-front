package com.nhnacademy.front.common.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

// @Configuration
public class FeignConfig {
    @Bean
    public Encoder feignFormEncoder() {
        ObjectFactory<HttpMessageConverters> messageConverters = () ->
            new HttpMessageConverters(new MappingJackson2HttpMessageConverter());

        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}

