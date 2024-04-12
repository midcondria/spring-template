package com.commerce.team.user.ui;

import com.commerce.team.global.config.security.UserPrincipal;
import com.commerce.team.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String user() {
        return "유저 페이지입니다.";
    }

    @GetMapping("/admin")
    public String amin(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println(userPrincipal.toString());
        return "관리자 페이지입니다.";
    }
}
