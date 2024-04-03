package com.commerce.team.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlainPasswordEncoderTest {

    @Autowired
    private PasswordEncoder encoder;

    @DisplayName("비밀번호 암호화 과정 확인용 테스트")
    @Test
    void encryptPassword() {
        // given
        String rawPassword = "asd123123";

        // when
        String result = encoder.encrypt(rawPassword);

        // then
        assertThat(result).isEqualTo("asd123123");
    }

    @DisplayName("비밀번호 일치 확인 과정 확인용 테스트")
    @Test
    void matchPassword() {
        // given
        String rawPassword = "asd123123";
        String encryptedPassword = encoder.encrypt(rawPassword);

        // when
        boolean result = encoder.matches(rawPassword, encryptedPassword);

        // then
        assertThat(result).isTrue();
    }
}