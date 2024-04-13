package com.commerce.team.global.config.security;

import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    // 역할: ROLE_ADMIN, 권한: ADMIN
    public UserPrincipal(com.commerce.team.user.domain.User user) {
        super(user.getEmail(), user.getPassword(),
            List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            ));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
