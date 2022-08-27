package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService
{
    public WebClientClass send(WebClient.RequestHeadersSpec<? extends WebClient.RequestHeadersSpec<?>> request, Class<? extends WebClientClass> objectDeserializerClass) {
        return request
                .retrieve()
                .bodyToMono(objectDeserializerClass)//Class I'm expecting
                .block();
    }
}
