package com.commerce.team.auth.application;

import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.global.config.AppConfig;
import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("이메일 중복 확인 시 중복된 이메일이 없으면 성공한다.")
    @Test
    void checkEmailDuplicate() {
        // given
        String email = "midcon@nav.com";

        assertThatCode(() -> authService.checkEmailDuplicate(email))
            .doesNotThrowAnyException();
    }

    @DisplayName("이메일 중복 확인 시 중복된 이메일이 있으면 커스텀 예외를 던진다.")
    @Test
    void userAlreadyExists() {
        // given
        User user = User.builder()
            .name("나는짱")
            .email("midcon@nav.com")
            .password("asd123123")
            .build();
        userRepository.save(user);

        // expected
        assertThatThrownBy(() -> authService.checkEmailDuplicate("midcon@nav.com"))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessageContaining("이미 존재하는 유저입니다.");
    }

    @DisplayName("회원가입 성공 시 jwt를 발급한다.")
    @Test
    void signup() {
        // given
        NormalSignupRequest request = NormalSignupRequest.builder()
            .name("나는짱")
            .email("midcon@nav.com")
            .password("asd123123")
            .build();

        // when
        String jwt = authService.signup(request);

        Jws<Claims> claimsJws = Jwts.parser()
            .verifyWith(appConfig.getSecretKey())
            .build()
            .parseSignedClaims(jwt);

        String result = claimsJws.getPayload().getSubject();

        // then
        assertThat(result).isEqualTo("나는짱");
    }
}