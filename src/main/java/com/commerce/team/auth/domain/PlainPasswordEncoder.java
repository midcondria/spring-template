package com.commerce.team.auth.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
@Slf4j
public class PlainPasswordEncoder implements PasswordEncoder{

    @Override
    public String encrypt(String rawPassword) {
        log.info("비밀번호 암호화 완료");
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
