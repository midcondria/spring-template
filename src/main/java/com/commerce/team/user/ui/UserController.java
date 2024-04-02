package com.commerce.team.user.ui;

import com.commerce.team.user.application.UserService;
import com.commerce.team.user.dto.UserSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public String signup(@RequestBody @Valid UserSignupRequest request) {
        userService.signup(request);
        return "유저 등록 완료";
    }
}
