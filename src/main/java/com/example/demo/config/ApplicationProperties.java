package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties
public class ApplicationProperties {
    @Value("${apiKey}")
    public String xApiKey;

    @Value("${apiBaseUrl}")
    public String apiBaseUrl;
}
