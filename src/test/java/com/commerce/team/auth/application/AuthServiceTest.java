package com.commerce.team.auth.application;

import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("중복된 이메일이 없을 때 사용 가능하다는 메시지를 출력한다.")
    @Test
    void test() {
        // given
        String email = "midcon@nav.com";

        assertThatCode(() -> authService.checkEmailDuplicate(email))
            .doesNotThrowAnyException();
    }

    @DisplayName("중복된 이메일일 때 커스텀 예외를 던진다.")
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
}