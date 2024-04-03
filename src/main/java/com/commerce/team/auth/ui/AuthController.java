package com.commerce.team.auth.ui;

import com.commerce.team.auth.application.AuthService;
import com.commerce.team.auth.dto.NormalSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid NormalSignupRequest request) {
        authService.signup(request);
        return "회원 가입 완료";
    }
}
