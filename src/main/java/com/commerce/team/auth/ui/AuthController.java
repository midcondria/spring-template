package com.commerce.team.auth.ui;

import com.commerce.team.auth.application.AuthService;
import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid NormalSignupRequest request) {
        authService.signup(request);
        ApiResponse response = ApiResponse.of("회원 가입 완료", null);
        return ResponseEntity
            .status(CREATED)
            .body(response);
    }
}
