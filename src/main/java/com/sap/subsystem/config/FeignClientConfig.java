package com.sap.subsystem.config;

import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class FeignClientConfig {

    @Value("${github.token}")
    private String accessToken;

    @Bean
    public RequestInterceptor requestInterceptor(){
        return (final RequestTemplate requestTemplate) -> {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            requestTemplate.header(HttpHeaders.ACCEPT, "application/vnd.github+json");
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, "application/json");
        };
    }

    @Bean
    public Client feignClient() {
        return new OkHttpClient();
    }
}
