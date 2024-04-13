package com.commerce.team.global.config.security.handler;

import com.commerce.team.global.config.security.UserPrincipal;
import com.commerce.team.global.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        log.error("[인증성공] user={}", principal.getUsername());
        ApiResponse<String> apiResponse = ApiResponse.of("로그인에 성공했습니다.", null);

        ResponseCookie cookie = ResponseCookie.from("jwt", "1234")
            .domain("localhost")   // todo 서버 환경에 따라 설정파일로 분리해서 관리하자.
            .path("/")
            .secure(false)
            .maxAge(Duration.ofDays(30))  // 한달이 국룰
            .sameSite("Strict")
            .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(SC_OK);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
