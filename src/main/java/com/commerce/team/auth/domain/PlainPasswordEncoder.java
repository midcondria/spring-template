package com.commerce.team.auth.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class PlainPasswordEncoder implements PasswordEncoder{

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
