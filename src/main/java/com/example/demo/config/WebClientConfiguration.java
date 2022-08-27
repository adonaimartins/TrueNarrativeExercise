package com.example.demo.config;

import com.example.demo.controller.headers.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration
{
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WebClient getWebClientBuilder(ApplicationProperties applicationProperties){
        return WebClient.builder()
                .baseUrl(applicationProperties.apiBaseUrl)
                .defaultHeader(HttpHeaders.X_API_KEY, applicationProperties.getXApiKey())
                .build();
    }
}
