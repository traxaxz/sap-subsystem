package com.sap.subsystem.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class GithubFeignInterceptor {

    @Value("${github.token}")
    private String accessToken;

    @Bean
    public RequestInterceptor requestInterceptor(){
        return (RequestTemplate requestTemplate) -> {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            requestTemplate.header(HttpHeaders.ACCEPT, "application/vnd.github+json");
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, "application/json");
        };
    }
}
