package com.commerce.team.global.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "midcon")
public class AppConfig {

    private final String jwtKey;
    private final String baseUrl;

    public SecretKey getSecretKey() {
        byte[] byteJwtKey = Decoders.BASE64.decode(this.jwtKey);
        return Keys.hmacShaKeyFor(byteJwtKey);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
