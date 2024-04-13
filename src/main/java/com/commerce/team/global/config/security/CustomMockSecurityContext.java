package com.commerce.team.global.config.security;

import com.commerce.team.user.domain.User;
import com.commerce.team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

// 테스트용이니 테스트에서 만들어도 될듯
@RequiredArgsConstructor
public class CustomMockSecurityContext implements WithSecurityContextFactory<CustomWithMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(CustomWithMockUser annotation) {
        User user = User.builder()
            .email("mid@nav.com")
            .password("asd123123")
            .name("나는짱")
            .build();
        userRepository.save(user);

        UserPrincipal principal = new UserPrincipal(user);

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,
            user.getPassword(),
            List.of(role));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        return context;
    }
}
