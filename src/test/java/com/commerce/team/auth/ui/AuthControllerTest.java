package com.commerce.team.auth.ui;

import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("올바른 정보 입력 시 회원가입에 성공한다.")
    @Test
    void signupSuccessTest() throws Exception {
        // given
        NormalSignupRequest request = NormalSignupRequest.builder()
            .name("나는짱")
            .email("midcon@nav.com")
            .password("1234")
            .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(
                post("/auth/signup")
                    .content(json)
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("회원 가입 완료"));
    }

    @DisplayName("빈 객체를 입력할 경우 에러 메시지를 출력한다.")
    @Test
    void signupFailTestWithNoData() throws Exception {
        // given
        NormalSignupRequest request = NormalSignupRequest.builder()
            .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(
                post("/auth/signup")
                    .content(json)
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("입력 값을 확인해주세요."))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data", Matchers.hasItems("이메일을 입력해주세요.", "비밀번호를 입력해주세요.", "이름을 입력해주세요.")));
    }

    @DisplayName("빈 값을 입력할 경우 에러 메시지를 출력한다.")
    @Test
    void signupFailTestWithEmptyData() throws Exception {
        // given
        NormalSignupRequest request = NormalSignupRequest.builder()
            .name("")
            .email("")
            .password("")
            .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(
                post("/auth/signup")
                    .content(json)
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("입력 값을 확인해주세요."))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data", Matchers.hasItems("이메일을 입력해주세요.", "비밀번호를 입력해주세요.", "이름을 입력해주세요.")));
    }
}
