package com.commerce.team.auth.ui;

import com.commerce.team.auth.application.AuthService;
import com.commerce.team.auth.dto.LoginRequest;
import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.global.dto.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    // Spring EL
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public String test(HttpServletRequest request) {
        log.info("메인 페이지 접속");
        return "hi";
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginRequest login, HttpServletRequest request) {
        authService.login();
        Cookie[] cookies = request.getCookies();
        log.info("로그인 페이지 접속, {}, {}",cookies.length, login);
        return "hi2";
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid NormalSignupRequest request) {
        String jwt = authService.signup(request);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
            .domain("localhost")   // todo 서버 환경에 따라 설정파일로 분리해서 관리하자.
            .path("/")
            .secure(false)
            .maxAge(Duration.ofDays(30))  // 한달이 국룰
            .sameSite("Strict")
            .build();

        return ResponseEntity
            .status(CREATED)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(ApiResponse.of("회원 가입 완료", null));
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "expired")
            .domain("localhost")   // todo 서버 환경에 따라 설정파일로 분리해서 관리하자.
            .path("/")
            .secure(false)
            .maxAge(0)
            .sameSite("Strict")
            .build();

        return ResponseEntity
            .status(OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(ApiResponse.of("로그아웃 완료", null));
    }
}
