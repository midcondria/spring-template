package com.commerce.team.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "midcon")
public class AppConfig {

    private final String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
